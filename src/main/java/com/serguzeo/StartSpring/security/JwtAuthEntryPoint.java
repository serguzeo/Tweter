package com.serguzeo.StartSpring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.serguzeo.StartSpring.configs.JacksonConfig;
import com.serguzeo.StartSpring.exceptions.ErrorDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private JacksonConfig jacksonConfig;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), "Authentication error", authException.getMessage());

        ObjectMapper objectMapper = jacksonConfig.objectMapper();
        String errorDetailsJson = objectMapper.writeValueAsString(errorDetails);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(errorDetailsJson);
    }

}
