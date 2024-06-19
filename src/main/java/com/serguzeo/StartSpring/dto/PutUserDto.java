package com.serguzeo.StartSpring.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PutUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String bio;
    private MultipartFile userPhoto;
}
