package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.exceptions.ResourceNotFoundException;
import com.serguzeo.StartSpring.mappers.UserMapper;
import com.serguzeo.StartSpring.models.Follow;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.repositories.IFollowRepository;
import com.serguzeo.StartSpring.repositories.IUserRepository;
import com.serguzeo.StartSpring.services.I.IFollowService;
import com.serguzeo.StartSpring.services.I.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;


@Service
@AllArgsConstructor
@Primary
public class FollowServiceImpl implements IFollowService {
    private final IFollowRepository followRepository;
    private final IUserRepository userRepository;
    private final IUserService userService;

    @Override
    public ResponseEntity<Map<String, String>> subscribe(Authentication authentication, UUID uuid) {
        UserEntity follower = userService.getUserEntityFromAuthentication(authentication);
        UserEntity following = getUserEntityByUuid(uuid);

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        try {
            followRepository.save(follow);
            return new ResponseEntity<>(Collections.singletonMap("response", "Subscribed!"), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ignored) {
            return new ResponseEntity<>(Collections.singletonMap("response", "Already subscribed"), HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<?> unsubscribe(Authentication authentication, UUID uuid) {
        UserEntity follower = userService.getUserEntityFromAuthentication(authentication);
        UserEntity following = getUserEntityByUuid(uuid);
        Optional<Follow> follow = followRepository.findByFollowerAndFollowing(follower, following);
        follow.ifPresent(followRepository::delete);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<UserDto>> getSubscriptions(Authentication authentication) {
        UserEntity user = userService.getUserEntityFromAuthentication(authentication);
        List<Follow> followList = followRepository.findByFollower(user);
        List<UserDto> subscriptions = mapFollowListToUserDtoList(followList, Follow::getFollowing);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getSubscriptions(UUID uuid) {
        UserEntity user = getUserEntityByUuid(uuid);
        List<Follow> followList = followRepository.findByFollower(user);
        List<UserDto> subscriptions = mapFollowListToUserDtoList(followList, Follow::getFollowing);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getFollowers(Authentication authentication) {
        UserEntity user = userService.getUserEntityFromAuthentication(authentication);
        List<Follow> followList = followRepository.findByFollowing(user);
        List<UserDto> followers = mapFollowListToUserDtoList(followList, Follow::getFollower);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserDto>> getFollowers(UUID uuid) {
        UserEntity user = getUserEntityByUuid(uuid);
        List<Follow> followList = followRepository.findByFollowing(user);
        List<UserDto> followers = mapFollowListToUserDtoList(followList, Follow::getFollower);
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    private List<UserDto> mapFollowListToUserDtoList(List<Follow> followList, Function<Follow, UserEntity> userExtractor) {
        return followList.stream()
                .map(follow -> UserMapper.INSTANCE.userEntityToUserDto(userExtractor.apply(follow)))
                .toList();
    }


    private UserEntity getUserEntityByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("No such user found"));
    }
}


