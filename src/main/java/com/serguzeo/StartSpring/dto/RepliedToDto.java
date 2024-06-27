package com.serguzeo.StartSpring.dto;

import lombok.Data;

@Data
public class RepliedToDto {
    private PublicationDto publication;
    private Boolean isDeleted;
}
