package com.serguzeo.StartSpring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;


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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "publication_uuid")
    private List<UserFile> files;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        publishedAt = LocalDateTime.now();
    }
}
