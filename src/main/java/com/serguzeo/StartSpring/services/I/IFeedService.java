package com.serguzeo.StartSpring.services.I;

import com.serguzeo.StartSpring.dto.PublicationWithUserDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IFeedService {
    List<PublicationWithUserDto> getFeed(Authentication authentication);
}
