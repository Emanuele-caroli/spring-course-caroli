package com.example.spring_course.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotNull
    private int eta;
    @NotBlank
    @Pattern(regexp = "([A-Z]{2})")
    private String citta;
    @NotBlank
    @Pattern(regexp = "([A-Z]{2})")
    private String nazione;

}
