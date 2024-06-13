package com.serguzeo.StartSpring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class FileUploadError extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 5L;

    public FileUploadError(String message) {
        super(message);
    }
}
