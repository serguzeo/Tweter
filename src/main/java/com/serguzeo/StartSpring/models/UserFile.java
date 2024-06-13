package com.serguzeo.StartSpring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;


@Data
@Table(name="files")
@Entity
public class UserFile {
    @Id
    private UUID uuid;
    private String path;
    private String name;
    private String originalFilename;
    private Long size;
    private String contentType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    private UserEntity user;

    @JsonIgnore
    public UserEntity getUser() {
        return user;
    }

    @JsonIgnore
    public String getPath() {
        return path;
    }

    public UserFile() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
