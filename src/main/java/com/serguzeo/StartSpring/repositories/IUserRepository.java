package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUuid(UUID uuid);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByUsernameOrEmail(String username, String email);
}
