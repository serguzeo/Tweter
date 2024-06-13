package com.serguzeo.StartSpring.repositories;


import com.serguzeo.StartSpring.models.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface IFileRepository extends JpaRepository<UserFile, UUID> {
    public Optional<UserFile> findUserFileByUuid(UUID uuid);
}
