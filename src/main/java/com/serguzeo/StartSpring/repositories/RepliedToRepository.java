package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.Publication;
import com.serguzeo.StartSpring.models.RepliedTo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RepliedToRepository extends JpaRepository<RepliedTo, UUID> {
    Optional<RepliedTo> findRepliedToByPublication (Publication publication);
}
