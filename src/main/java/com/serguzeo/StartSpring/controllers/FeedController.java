package com.serguzeo.StartSpring.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/feed")
public class FeedController {
    @GetMapping
    public String getFeed() {
        return "Hello World";
    }
}
