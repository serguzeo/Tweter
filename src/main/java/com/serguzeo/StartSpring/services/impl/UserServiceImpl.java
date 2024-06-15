package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.exceptions.ResourceNotFoundException;
import com.serguzeo.StartSpring.mappers.UserMapper;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.models.UserFile;
import com.serguzeo.StartSpring.repositories.IFileRepository;
import com.serguzeo.StartSpring.repositories.IUserRepository;
import com.serguzeo.StartSpring.services.I.IFileService;
import com.serguzeo.StartSpring.services.I.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements IUserService {
    IUserRepository repository;
    IFileService fileService;
    IFileRepository fileRepository;

    @Override
    public ResponseEntity<UserDto> findByUuid(UUID uuid) {
        Optional<UserEntity> user = repository.findByUuid(uuid);
        return createResponse(user);
    }

    @Override
    public ResponseEntity<UserDto> findByUsername(String username) {
        Optional<UserEntity> user = repository.findByUsername(username);
        return createResponse(user);
    }

    @Override
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        String username = authentication.getName();
        Optional<UserEntity> user = repository.findByUsername(username);
        return createResponse(user);
    }

    @Override
    public ResponseEntity<UserDto> setProfilePhoto(Authentication authentication, MultipartFile file) {
        String username = authentication.getName();
        Optional<UserEntity> userOptional = repository.findByUsername(username);
        UserEntity user = userOptional.orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found"));

        if (user.getUserPhoto() != null) {
            fileService.deleteUserFile(user.getUserPhoto().getUuid());
        }
        UserFile userFile = fileService.saveUserFile(user, file);

        user.setUserPhoto(userFile);
        repository.save(user);

        UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    private ResponseEntity<UserDto> createResponse(Optional<UserEntity> user) {
        if (user.isPresent()) {
            UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user.get());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No such user found");
        }
    }



}
