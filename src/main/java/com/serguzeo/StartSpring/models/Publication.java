package com.serguzeo.StartSpring.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="publications")
public class Publication {
    @Id
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private UserEntity user;
    private String text;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @PrePersist
    protected void onCreate() {
        publishedAt = LocalDateTime.now();
    }
}
