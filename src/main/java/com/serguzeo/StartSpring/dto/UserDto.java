package com.serguzeo.StartSpring.dto;

import com.serguzeo.StartSpring.models.UserFile;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserDto {
    private UUID uuid;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private UserFile userPhoto;
}
