package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.Follow;
import com.serguzeo.StartSpring.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFollowRepository extends JpaRepository<Follow, UUID> {
    List<Follow> findByFollower(UserEntity user);
    List<Follow> findByFollowing(UserEntity user);
    Optional<Follow> findByFollowerAndFollowing(UserEntity follower, UserEntity following);
}
