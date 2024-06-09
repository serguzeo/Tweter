package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.dto.AuthResponseDto;
import com.serguzeo.StartSpring.dto.LoginDto;
import com.serguzeo.StartSpring.dto.RegisterDto;
import com.serguzeo.StartSpring.services.I.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private IAuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }
}
