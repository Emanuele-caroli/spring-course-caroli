package com.example.spring_course.service;

import com.example.spring_course.service.dto.UserRequestDto;
import com.example.spring_course.service.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DemoService {

    private final Map<UUID, UserRequestDto> fakeDatabase = new HashMap<>();

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        UUID nuovoId =  UUID.randomUUID();
        fakeDatabase.put(nuovoId, userRequestDto);

        return new UserResponseDto(
                "Utente salvato con successo",
                nuovoId.toString()
        );
    }

    public boolean deleteUser(UUID id) {
        System.out.println("ID cercato: " + id);
        System.out.println("ID presenti nel DB: " + fakeDatabase.keySet());
        if (fakeDatabase.containsKey(id)) {
            fakeDatabase.remove(id);
            return true;
        }
        return false;
    }

    public UserRequestDto getUserById(UUID nuovoId) {
        return fakeDatabase.get(nuovoId);
    }

    public UserResponseDto updateUser(UUID id, UserRequestDto newData) {
        if (!fakeDatabase.containsKey(id)) {
            return null;
        }
        fakeDatabase.put(id, newData);

        return new UserResponseDto(
                "Utente aggiornato con successo",
                id.toString()
        );
    }

    public List<UserRequestDto> getAllUsers(String nome) {
        List<UserRequestDto> allUsers = new ArrayList<>(fakeDatabase.values());

        if (nome == null || nome.isEmpty()) {
            return allUsers;
        }

        return allUsers.stream()
                .filter(utente -> nome.equalsIgnoreCase(utente.getNome()))
                .toList();
    }


}
