package com.serguzeo.StartSpring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.serguzeo.StartSpring.models.UserFile;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PublicationDto {
    private UUID uuid;
    private String text;
    private RepliedToDto repliedTo;
    private List<UserFile> files;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime publishedAt;
}
