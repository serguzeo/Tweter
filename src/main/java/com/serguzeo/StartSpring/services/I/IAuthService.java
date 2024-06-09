package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.AuthResponseDto;
import com.serguzeo.StartSpring.dto.LoginDto;
import com.serguzeo.StartSpring.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IAuthService {
    ResponseEntity<AuthResponseDto> login(LoginDto loginDto);
    ResponseEntity<Map<String, String>> register(RegisterDto registerDto);
}
