package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.dto.PublicationWithUserDto;
import com.serguzeo.StartSpring.services.I.IFeedService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {
    private final IFeedService feedService;

    @GetMapping
    public List<PublicationWithUserDto> getFeed(Authentication authentication) {
        return feedService.getFeed(authentication);
    }
}
