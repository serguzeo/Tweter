package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.PutUserDto;
import com.serguzeo.StartSpring.dto.UserDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUserService {
    ResponseEntity<UserDto> findByUuid(UUID uuid);
    ResponseEntity<UserDto> findByUsername(String username);
    ResponseEntity<UserDto> getProfile(Authentication authentication);
    ResponseEntity<UserDto> updateProfile(Authentication authentication, PutUserDto putUserDto) throws BadRequestException;
}
