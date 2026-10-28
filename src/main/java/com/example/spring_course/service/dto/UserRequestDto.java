package com.example.spring_course.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequestDto {
    private UUID id;
    private String nome;
    private String email;
    private int eta;
    private String indirizzo;
}
