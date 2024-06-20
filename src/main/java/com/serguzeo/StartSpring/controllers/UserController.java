package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.dto.PublicationDto;
import com.serguzeo.StartSpring.dto.PutUserDto;
import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.services.I.IFollowService;
import com.serguzeo.StartSpring.services.I.IPublicationService;
import com.serguzeo.StartSpring.services.I.IUserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private IUserService userService;
    private IFollowService followService;
    private IPublicationService publicationService;

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

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateProfile(Authentication authentication, @ModelAttribute PutUserDto putUserDto)
            throws BadRequestException {
        return userService.updateProfile(authentication, putUserDto);
    }

    @GetMapping("/{uuid}/followers")
    public ResponseEntity<List<UserDto>> getFollowersByUuid(@PathVariable String uuid) {
        return followService.getFollowers(UUID.fromString(uuid));
    }

    @GetMapping("/{uuid}/subscriptions")
    public ResponseEntity<List<UserDto>> getSubscriptionsByUuid(@PathVariable String uuid) {
        return followService.getSubscriptions(UUID.fromString(uuid));
    }

    @PostMapping("/{uuid}/subscribe")
    public ResponseEntity<Map<String, String>> subscribe(Authentication authentication, @PathVariable String uuid) {
        return followService.subscribe(authentication, UUID.fromString(uuid));
    }

    @DeleteMapping("/{uuid}/unsubscribe")
    public ResponseEntity<?> unsubscribe(Authentication authentication, @PathVariable String uuid) {
        return followService.unsubscribe(authentication, UUID.fromString(uuid));
    }

    @GetMapping("/{uuid}/publications")
    public ResponseEntity<List<PublicationDto>> getPublicationsByUserUuid(@PathVariable String uuid) {
        return publicationService.findPublicationsByUserUuid(UUID.fromString(uuid));
    }
}
