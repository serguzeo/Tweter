package com.serguzeo.StartSpring.services.I;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.models.UserFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IFileService {
    ResponseEntity<?> getUserFileByUuid(UUID uuid);
    UserFile saveUserFile(UserEntity user, MultipartFile file);
    void deleteUserFile(UUID fileUuid);
}
