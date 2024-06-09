package com.serguzeo.StartSpring.repositories;

import com.serguzeo.StartSpring.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
