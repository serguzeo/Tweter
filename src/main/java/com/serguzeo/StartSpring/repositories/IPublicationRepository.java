package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.Publication;
import com.serguzeo.StartSpring.models.RepliedTo;
import com.serguzeo.StartSpring.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPublicationRepository extends JpaRepository<Publication, UUID> {
    Optional<Publication> findByUuid(UUID uuid);
    List<Publication> findByUser(UserEntity user);
    List<Publication> findByRepliedTo(RepliedTo repliedTo);
}
