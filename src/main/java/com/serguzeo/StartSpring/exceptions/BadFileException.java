package com.serguzeo.StartSpring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class BadFileException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4L;

    public BadFileException(String message) {
        super(message);
    }
}
