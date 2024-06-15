package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IFollowRepository extends JpaRepository<Follow, UUID> {
}
