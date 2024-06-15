package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.services.I.IFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {
    private final IFileService fileService;

    @GetMapping("/{uuid}")
    public ResponseEntity<?> findFile(@PathVariable String uuid) {
        return fileService.getUserFileByUuid(UUID.fromString(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteFile(@PathVariable String uuid) {
        fileService.deleteUserFile(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }
}
