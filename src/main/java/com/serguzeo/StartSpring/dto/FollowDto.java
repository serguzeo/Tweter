package com.serguzeo.StartSpring.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FollowDto {
    private UUID uuid;
    private UserDto follower;
    private UserDto following;
}
