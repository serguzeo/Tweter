package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.AuthResponseDto;
import com.serguzeo.StartSpring.dto.LoginDto;
import com.serguzeo.StartSpring.dto.RegisterDto;
import com.serguzeo.StartSpring.models.Role;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.repositories.IRoleRepository;
import com.serguzeo.StartSpring.repositories.IUserRepository;
import com.serguzeo.StartSpring.security.TokenGenerator;
import com.serguzeo.StartSpring.services.I.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@AllArgsConstructor
@Primary
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    @Override
    public ResponseEntity<AuthResponseDto> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getLogin(),
                            loginDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenGenerator.generateToken(authentication);
            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(new AuthResponseDto(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> register(RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>(Collections.singletonMap("response", "Username is taken!"), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>(Collections.singletonMap("response", "Email is already in use!"), HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setDateOfBirth(registerDto.getDateOfBirth());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);

        return new ResponseEntity<>(Collections.singletonMap("response", "User registered successfully!"), HttpStatus.CREATED);
    }
}
