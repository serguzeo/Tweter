package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    ResponseEntity<UserDto> findByUuid(UUID uuid);
    ResponseEntity<UserDto> getProfile(Authentication authentication);
}
