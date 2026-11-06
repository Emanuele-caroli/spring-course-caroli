package com.example.spring_course.controller;

import com.example.spring_course.model.UserRequest;
import com.example.spring_course.model.UserResponse;
import com.example.spring_course.service.DemoService;
import com.example.spring_course.service.dto.UserRequestDto;
import com.example.spring_course.service.dto.UserResponseDto;
import com.example.spring_course.service.mapper.MapperUserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Controller v1", description = "Controller di test")
public class DemoController {

    private static final Logger log = LoggerFactory.getLogger(DemoController.class);
    private final MapperUserDto mapperUserDto;
    private final DemoService demoService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getUserById(@Valid @PathVariable("id") String id) {

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

        return ResponseEntity.ok().body(utenteTrovato);
    }

    @Operation(summary = "Get all users", description = "Restituisce una lista con tutti gli utenti")
    @ApiResponse(
            responseCode = "200",
            description = "Lista recuperata correttamente",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping("/users")
    public ResponseEntity<List<UserRequestDto>> getAllUsers(
            @Valid @RequestParam(required = false) String nome
    ) {
        List<UserRequestDto> users = demoService.getAllUsers(nome);

        return ResponseEntity.ok().body(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@Valid @PathVariable("id") String id) {

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

    @ApiResponse(
            responseCode = "200",
            description = "Lista recuperata correttamente",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(
                            """
                                    {
                                          "messaggio": "Utente salvato con successo",
                                          "idGenerato": "83957c37-254f-4ea8-bb61-6209b2960273"
                                    }
                            """
                    ),
                    schema = @Schema(implementation = UserResponse.class)))
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest user) {

        log.info("Ricevuta richiesta per creare un nuovo utente con email: {}", user.getEmail());

        UserRequestDto requestDto = mapperUserDto.toRequestDto(user);

        UserResponseDto responseDto = demoService.createUser(requestDto);

        UserResponse webResponse = mapperUserDto.toResponse(responseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }
}