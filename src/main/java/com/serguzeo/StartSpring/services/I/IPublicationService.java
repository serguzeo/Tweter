package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.NewPublicationDto;
import com.serguzeo.StartSpring.dto.PublicationDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.UUID;

public interface IPublicationService {
    ResponseEntity<PublicationDto> findPublicationByUuid(UUID uuid);
    ResponseEntity<PublicationDto> savePublication(Authentication authentication, NewPublicationDto newPublicationDto);
    void deletePublication(UUID publicationUuid);
    ResponseEntity<PublicationDto> updatePublication(UUID publicationUuid, NewPublicationDto newPublicationDto);
}
