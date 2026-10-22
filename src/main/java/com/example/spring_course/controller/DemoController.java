package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.DemoService;
import com.example.spring_course.service.dto.UserRequestDto;
import com.example.spring_course.service.dto.UserResponseDto;
import com.example.spring_course.service.mapper.MapperUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DemoController {

    private final MapperUserDto mapperUserDto;
    private final DemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") String id) {

        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body("ID mancante");
        }

        UUID uuidRicercato;
        try {
            uuidRicercato = UUID.fromString(id);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Formato ID non valido");
        }

        UserRequestDto utenteTrovato = demoService.getUserById(uuidRicercato);

        if (utenteTrovato == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
        }

        return ResponseEntity.ok().body(utenteTrovato);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserRequestDto>> getAllUsers(
            @RequestParam(required = false) String nome
    ) {
        List<UserRequestDto> users = demoService.getAllUsers(nome);

        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") String id) {

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Formato ID non valido");
        }

        boolean eliminato = demoService.deleteUser(uuid);

        if (eliminato) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("id") String id,
            @Valid @RequestBody UserRequest userRequest) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Formato ID non valido");
        }
        UserRequestDto userRequestDto = mapperUserDto.toRequestDto(userRequest);
        UserResponseDto userResponseDto = demoService.updateUser(uuid, userRequestDto);

        if (userResponseDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utente non trovato");
        }
        return ResponseEntity.ok().body(userResponseDto);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {

        UserRequestDto requestDto = mapperUserDto.toRequestDto(user);

        UserResponseDto responseDto = demoService.createUser(requestDto);

        UserResponse webResponse = mapperUserDto.toResponse(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }
}