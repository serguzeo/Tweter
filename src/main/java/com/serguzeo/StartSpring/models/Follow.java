package com.serguzeo.StartSpring.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "follows")
public class Follow {
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "follower_uuid")
    private UserEntity follower;

    @ManyToOne
    @JoinColumn(name = "following_uuid")
    private UserEntity following;
}
