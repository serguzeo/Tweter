package com.serguzeo.StartSpring.services.impl;

import com.github.slugify.Slugify;
import com.serguzeo.StartSpring.exceptions.FileUploadError;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.models.UserFile;
import com.serguzeo.StartSpring.repositories.IFileRepository;
import com.serguzeo.StartSpring.services.I.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;


@Service
@Primary
public class FileServiceImpl implements IFileService {
    @Value("${files.upload.directory}")
    private String uploadDirectory;
    private final IFileRepository fileRepository;
    final Slugify slg = Slugify.builder().underscoreSeparator(true).build();

    @Autowired
    public FileServiceImpl(IFileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public ResponseEntity<?> getUserFileByUuid(UUID uuid) {
        Optional<UserFile> userFileOptional = fileRepository.findUserFileByUuid(uuid);

        if (userFileOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserFile userFile = userFileOptional.get();

        File file = new File(userFile.getPath());
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", userFile.getOriginalFilename());
        headers.setContentType(MediaType.parseMediaType(userFile.getContentType()));

        FileSystemResource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @Override
    public UserFile saveUserFile(UserEntity user, MultipartFile file) {
        try {
            Path userDir = Paths.get(uploadDirectory, String.valueOf(user.getUuid()));
            if (!Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }

            UUID fileUuid = UUID.randomUUID();

            String filename = slg.slugify(file.getOriginalFilename());
            int lastIndex = filename.lastIndexOf('_');
            if (lastIndex != -1) {
                filename = filename.substring(0, lastIndex) + "." + filename.substring(lastIndex + 1);
            }

            String systemFileName = fileUuid + "_" + filename;
            Path filePath = userDir.resolve(systemFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            UserFile userFile = new UserFile();
            userFile.setUuid(fileUuid);
            userFile.setPath(filePath.toString());
            userFile.setName(systemFileName);
            userFile.setOriginalFilename(filename);
            userFile.setSize(file.getSize());
            userFile.setContentType(file.getContentType());
            userFile.setUser(user);

            fileRepository.save(userFile);

            return userFile;
        } catch (IOException e) {
            throw new FileUploadError("Failed to save file");
        }
    }

    @Override
    public void deleteUserFile(UUID fileUuid) {
        Optional<UserFile> userFileOptional = fileRepository.findById(fileUuid);

        if (userFileOptional.isPresent()) {
            UserFile userFile = userFileOptional.get();
            Path filePath = Paths.get(userFile.getPath());

            try {
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete file");
            }

            UserEntity user = userFile.getUser();
            user.setUserPhoto(null);

            fileRepository.delete(userFile);
        }
    }
}
