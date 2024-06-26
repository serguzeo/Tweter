package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.PublicationWithUserDto;
import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.mappers.PublicationWithUserMapper;
import com.serguzeo.StartSpring.models.Follow;
import com.serguzeo.StartSpring.models.Publication;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.repositories.IFollowRepository;
import com.serguzeo.StartSpring.repositories.IPublicationRepository;
import com.serguzeo.StartSpring.services.I.IFeedService;
import com.serguzeo.StartSpring.services.I.IFollowService;
import com.serguzeo.StartSpring.services.I.IPublicationService;
import com.serguzeo.StartSpring.services.I.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class FeedServiceImpl implements IFeedService {
    private IUserService userService;
    private IFollowRepository followRepository;
    private IPublicationRepository publicationRepository;

    @Override
    public List<PublicationWithUserDto> getFeed(Authentication authentication) {

        UserEntity user = userService.getUserEntityFromAuthentication(authentication);

        // Get list of follow entities
        List<Follow> subscribes = followRepository.findByFollower(user);

        // Find users that you followed
        List<UserEntity> followingList = new ArrayList<>(subscribes.stream()
                .map(Follow::getFollowing)
                .toList());
        followingList.add(user);

        // Get all publications that you need
        List<Publication> publicationsList = followingList.stream()
                .flatMap(userEntity -> publicationRepository.findByUser(userEntity).stream())
                .toList();

        // Make dto
        List<PublicationWithUserDto> publicationWithUserDtoList = new ArrayList<>(publicationsList.stream()
                .map(PublicationWithUserMapper.INSTANCE::publicationToPublicationDto)
                .toList());

        publicationWithUserDtoList.sort(Comparator.comparing(PublicationWithUserDto::getPublishedAt).reversed());

        return publicationWithUserDtoList;
    }
}
