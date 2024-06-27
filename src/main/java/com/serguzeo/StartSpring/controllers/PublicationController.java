package com.serguzeo.StartSpring.controllers;

import com.serguzeo.StartSpring.dto.NewPublicationDto;
import com.serguzeo.StartSpring.dto.PublicationDto;
import com.serguzeo.StartSpring.repositories.IPublicationRepository;
import com.serguzeo.StartSpring.services.I.IPublicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/publications")
public class PublicationController {
    private final IPublicationService publicationService;
    private final IPublicationRepository publicationRepository;

    @GetMapping("/{uuid}")
    public ResponseEntity<PublicationDto> findPublication (@PathVariable String uuid) {
        return publicationService.findPublicationByUuid(UUID.fromString(uuid));
    }

    @PostMapping
    public ResponseEntity<PublicationDto> createPublication (
            Authentication authentication,
            @ModelAttribute NewPublicationDto newPublicationDto
    ) {
        return publicationService.savePublication(authentication, newPublicationDto);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deletePublication (@PathVariable String uuid) {
        publicationService.deletePublication(UUID.fromString(uuid));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PublicationDto> updatePublication (
            @PathVariable String uuid,
            @ModelAttribute NewPublicationDto newPublicationDto
    ) {
        return publicationService.updatePublication(UUID.fromString(uuid), newPublicationDto);
    }
}
