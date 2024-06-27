package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUuid(UUID uuid);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    List<UserEntity> findByUsernameStartsWithIgnoreCase(String username);
    @Query("SELECT u FROM UserEntity u WHERE UPPER(CONCAT(u.firstName, ' ', u.lastName)) LIKE UPPER(CONCAT(:prefix, '%'))")
    List<UserEntity> findByFullNameStartsWithIgnoreCase(@Param("prefix") String prefix);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByUsernameOrEmail(String username, String email);
}
