package com.example.spring_course.service.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String nome;
    private String email;
    private int eta;
    private String indirizzo;
}
