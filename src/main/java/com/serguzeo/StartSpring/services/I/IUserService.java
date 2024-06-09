package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.models.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    ResponseEntity<UserDto> findByUuid(UUID uuid);
}
