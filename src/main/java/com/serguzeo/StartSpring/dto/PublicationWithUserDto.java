package com.serguzeo.StartSpring.dto;

import lombok.Data;
import org.springframework.security.core.parameters.P;

@Data
public class PublicationWithUserDto extends PublicationDto {
    private UserDto user;
}
