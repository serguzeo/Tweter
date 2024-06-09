package com.serguzeo.StartSpring.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterDto {
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String password;
}
