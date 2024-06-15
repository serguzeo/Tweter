package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.services.I.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private IUserService userService;

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDto> findUser (@PathVariable String uuid) {
        return userService.findByUuid(UUID.fromString(uuid));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findUserByUsername (@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getProfile(Authentication authentication) {
        return userService.getProfile(authentication);
    }

    @PostMapping("/me/setProfilePhoto")
    public ResponseEntity<UserDto> setProfilePhoto (Authentication authentication, @RequestParam MultipartFile file) {
        return userService.setProfilePhoto(authentication, file);
    }
}
