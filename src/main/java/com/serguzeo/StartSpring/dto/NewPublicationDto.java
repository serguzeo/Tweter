package com.serguzeo.StartSpring.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
public class NewPublicationDto {
    private String text;
    private List<MultipartFile> files;
    private String repliedTo;
}
