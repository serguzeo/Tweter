package com.serguzeo.StartSpring.dto;

import lombok.Data;

@Data
public class RepliedToDto {
    private PublicationWithUserDto publication;
    private Boolean isDeleted;
}
