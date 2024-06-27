package com.serguzeo.StartSpring.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "replied_to")
@NoArgsConstructor
public class RepliedTo {
    @Id
    private UUID uuid;
    @OneToOne
    private Publication publication;
    private Boolean isDeleted;

    public RepliedTo(Publication publication) {
        this.publication = publication;
    }

    @PrePersist
    public void prePersist() {
        isDeleted = Boolean.FALSE;
        uuid = UUID.randomUUID();
    }
}
