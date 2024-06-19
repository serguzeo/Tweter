package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.dto.FollowDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {
    

    @GetMapping
    public List<FollowDto> getFollows(Authentication authentication) {
        return null;
    }
}
