package com.serguzeo.StartSpring.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    private UUID uuid;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String bio;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "photo_uuid")
    private UserFile userPhoto;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        bio = "Hello! I am using Tweter! I am very happy!";
    }
}
