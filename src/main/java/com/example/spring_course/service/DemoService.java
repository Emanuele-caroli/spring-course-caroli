package com.example.spring_course.service;

import com.example.spring_course.repository.model.UserEntity;
import com.example.spring_course.repository.UserRepository;
import com.example.spring_course.service.dto.UserRequestDto;
import com.example.spring_course.service.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        try {
            UserEntity user = new UserEntity();

            user.setNome(userRequestDto.getNome());
            user.setEmail(userRequestDto.getEmail());
            user.setEta(userRequestDto.getEta());
            user.setIndirizzo(userRequestDto.getIndirizzo());

            userRepository.save(user);

            return new UserResponseDto(
                    "Utente salvato con successo",
                    user.getId().toString()
            );
        } catch (Exception e) {
            return new UserResponseDto(
                    "Errore durante il salvataggio: " + e.getMessage(),
                    null
            );
        }
    }

    public boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public UserRequestDto getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto newData) {
        return userRepository.findById(id).map(user -> {
            user.setNome(newData.getNome());
            userRepository.save(user);

            return new UserResponseDto(
                    "Utente aggiornato con successo",
                    id.toString()
            );
        }).orElse(null);
    }

    public List<UserRequestDto> getAllUsers(String email) {
        List<UserEntity> users;

        if (email == null || email.isEmpty()) {
            users = userRepository.findAll();
        } else {
            users = userRepository.findByEmail(email);
        }

        return users.stream()
                .map(this::convertToDto)
                .toList();
    }

    private UserRequestDto convertToDto(UserEntity user) {
        UserRequestDto dto = new UserRequestDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setEta(user.getEta());
        dto.setIndirizzo(user.getIndirizzo());

        return dto;
    }
}