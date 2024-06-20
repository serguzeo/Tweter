package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.models.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IFollowService {
    ResponseEntity<Map<String, String>> subscribe(Authentication authentication, UUID uuid);
    ResponseEntity<?> unsubscribe(Authentication authentication, UUID uuid);
    ResponseEntity<List<UserDto>> getFollowers(UUID uuid);
    ResponseEntity<List<UserDto>> getSubscriptions(UUID uuid);
}
