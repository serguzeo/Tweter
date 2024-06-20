package com.serguzeo.StartSpring.services.impl;

import com.serguzeo.StartSpring.dto.NewPublicationDto;
import com.serguzeo.StartSpring.dto.PublicationDto;
import com.serguzeo.StartSpring.exceptions.ResourceNotFoundException;
import com.serguzeo.StartSpring.mappers.PublicationMapper;
import com.serguzeo.StartSpring.models.Publication;
import com.serguzeo.StartSpring.models.UserEntity;
import com.serguzeo.StartSpring.models.UserFile;
import com.serguzeo.StartSpring.repositories.IPublicationRepository;
import com.serguzeo.StartSpring.repositories.IUserRepository;
import com.serguzeo.StartSpring.services.I.IFileService;
import com.serguzeo.StartSpring.services.I.IPublicationService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class PublicationServiceImpl implements IPublicationService {
    IPublicationRepository publicationRepository;
    IUserRepository userRepository;
    IFileService fileService;

    @Override
    public ResponseEntity<PublicationDto> findPublicationByUuid(UUID uuid) {
        return createResponse(publicationRepository.findByUuid(uuid));
    }

    @Override
    public ResponseEntity<PublicationDto> savePublication(Authentication authentication, NewPublicationDto newPublicationDto){
        String username = authentication.getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        UserEntity user = userOptional.orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found"));

        Publication publication = new Publication();
        publication.setUser(user);
        publication.setText(newPublicationDto.getText());

        if (newPublicationDto.getFiles() != null && !newPublicationDto.getFiles().isEmpty()) {
            List<UserFile> files = new ArrayList<>();
            newPublicationDto.getFiles().forEach(file -> {
                if (file.getSize() == 0)
                    return;
                UserFile userFile = fileService.saveUserFile(user, file);
                files.add(userFile);
            });
            publication.setFiles(files);
        }

        publicationRepository.save(publication);

        PublicationDto pDto = PublicationMapper.INSTANCE.publicationToPublicationDto(publication);
        return new ResponseEntity<>(pDto, HttpStatus.CREATED);
    }

    @Override
    public void deletePublication(UUID publicationUuid) {
        Optional<Publication> publicationOptional = publicationRepository.findByUuid(publicationUuid);
        if (publicationOptional.isEmpty()) {
            return;
        }
        Publication publication = publicationOptional.get();

        List<UserFile> files = publication.getFiles();
        files.forEach(file -> fileService.deleteUserFile(file.getUuid()));

        publicationRepository.delete(publication);
    }

    @Override
    public ResponseEntity<PublicationDto> updatePublication(UUID publicationUuid, NewPublicationDto newPublicationDto) {
        Publication publication = publicationRepository.findByUuid(publicationUuid)
                .orElseThrow(() -> new ResourceNotFoundException("No such publication found: " + publicationUuid));

        if (newPublicationDto.getText() != null) {
            publication.setText(newPublicationDto.getText());
        }

        if (newPublicationDto.getFiles() != null && !newPublicationDto.getFiles().isEmpty()) {
            List<UserFile> updatedFiles = publication.getFiles();
            newPublicationDto.getFiles().forEach(file -> {
                if (file.getSize() == 0)
                    return;
                UserFile userFile = fileService.saveUserFile(publication.getUser(), file);
                updatedFiles.add(userFile);
            });
            publication.setFiles(updatedFiles);
        }

        // Сохраняем обновленную публикацию
        publicationRepository.save(publication);

        PublicationDto publicationDto = PublicationMapper.INSTANCE.publicationToPublicationDto(publication);
        return new ResponseEntity<>(publicationDto, HttpStatus.OK);
    }

    private ResponseEntity<PublicationDto> createResponse(Optional<Publication> publication) {
        if (publication.isPresent()) {
            PublicationDto publicationDto = PublicationMapper.INSTANCE.publicationToPublicationDto(publication.get());
            return new ResponseEntity<>(publicationDto, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No such publication found");
        }
    }
}
