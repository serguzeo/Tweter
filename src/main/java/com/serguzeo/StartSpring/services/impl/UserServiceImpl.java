package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.exceptions.ResourceNotFoundException;
import com.serguzeo.StartSpring.mappers.UserMapper;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.repositories.IUserRepository;
import com.serguzeo.StartSpring.services.I.IUserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements IUserService {
    IUserRepository repository;

    @Override
    public ResponseEntity<UserDto> findByUuid(UUID uuid) {
        Optional<UserEntity> user = repository.findByUuid(uuid);

        if (user.isPresent()) {
            UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user.get());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No such user found");
        }
    }
}
