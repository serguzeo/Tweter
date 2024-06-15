package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IPublicationRepository extends JpaRepository<Publication, UUID> {
    Optional<Publication> findByUuid(UUID uuid);
}
