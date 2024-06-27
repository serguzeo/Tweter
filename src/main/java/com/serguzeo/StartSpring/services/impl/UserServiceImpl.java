package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.PutUserDto;
import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.exceptions.ResourceNotFoundException;
import com.serguzeo.StartSpring.mappers.UserMapper;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.models.UserFile;
import com.serguzeo.StartSpring.repositories.IFileRepository;
import com.serguzeo.StartSpring.repositories.IUserRepository;
import com.serguzeo.StartSpring.services.I.IFileService;
import com.serguzeo.StartSpring.services.I.IUserService;
import info.debatty.java.stringsimilarity.JaroWinkler;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements IUserService {
    private final IUserRepository repository;
    private final IFileService fileService;
    private final IFileRepository fileRepository;
    private final JaroWinkler jaroWinkler = new JaroWinkler();

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
    public ResponseEntity<List<UserDto>> findByPrefix(String prefix, Integer count) {
        List<UserEntity> usersByUsername = repository.findByUsernameStartsWithIgnoreCase(prefix);
        List<UserEntity> usersByFullName = repository.findByFullNameStartsWithIgnoreCase(prefix);

        List<UserEntity> combinedUsers = new java.util.ArrayList<>(usersByUsername);

        combinedUsers.addAll(
                usersByFullName.stream()
                        .filter(user -> !usersByUsername.contains(user))
                        .toList()
        );

        combinedUsers.sort(Comparator.comparing((UserEntity user) -> {
            String fullName = user.getFirstName() + " " + user.getLastName();
            double similarityUsername = jaroWinkler.similarity(user.getUsername(), prefix);
            double similarityFullName = jaroWinkler.similarity(fullName, prefix);
            return Math.max(similarityUsername, similarityFullName);
        }).reversed());

        List<UserDto> userDtoList = combinedUsers.stream()
                .limit(count > 0 ? count : combinedUsers.size())
                .map(UserMapper.INSTANCE::userEntityToUserDto)
                .toList();

        return ResponseEntity.ok(userDtoList);
    }

    @Override
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        UserEntity user = getUserEntityFromAuthentication(authentication);
        UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> updateProfile(Authentication authentication, PutUserDto putUserDto) throws BadRequestException {
        UserEntity user = getUserEntityFromAuthentication(authentication);

        Optional.ofNullable(putUserDto.getUserPhoto())
                .ifPresent(photo -> setProfilePhoto(user, photo));

        updateUserDetails(user, putUserDto);

        UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(repository.save(user));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    private void setProfilePhoto(UserEntity user, MultipartFile file) {
        if (user.getUserPhoto() != null) {
            fileService.deleteUserFile(user.getUserPhoto().getUuid());
        }
        UserFile userFile = fileService.saveUserFile(user, file);

        user.setUserPhoto(userFile);
        repository.save(user);
    }

    private void updateUserDetails(UserEntity user, PutUserDto putUserDto) throws BadRequestException {
        updateUsername(user, putUserDto.getUsername());
        updateEmail(user, putUserDto.getEmail());
        updateOptionalField(putUserDto.getFirstName(), user::setFirstName);
        updateOptionalField(putUserDto.getLastName(), user::setLastName);
        updateOptionalField(putUserDto.getBio(), user::setBio);
    }

    private void updateUsername(UserEntity user, String username) throws BadRequestException {
        if (username != null) {
            if (repository.existsByUsername(username)) {
                throw new BadRequestException("Username already exists");
            }
            user.setUsername(username);
        }
    }

    private void updateEmail(UserEntity user, String email) throws BadRequestException {
        if (email != null) {
            if (repository.existsByEmail(email)) {
                throw new BadRequestException("Email already exists");
            }
            user.setEmail(email);
        }
    }

    private void updateOptionalField(String value, Consumer<String> setter) {
        Optional.ofNullable(value).ifPresent(setter);
    }

    private ResponseEntity<UserDto> createResponse(Optional<UserEntity> user) {
        if (user.isPresent()) {
            UserDto userDto = UserMapper.INSTANCE.userEntityToUserDto(user.get());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No such user found");
        }
    }

    @Override
    public UserEntity getUserEntityFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        Optional<UserEntity> userOptional = repository.findByUsername(username);
        return userOptional.orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found"));
    }

    @Override
    public UserEntity getUserEntityByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("No such user found"));
    }
}
