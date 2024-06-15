package com.serguzeo.StartSpring.mappers;

import com.serguzeo.StartSpring.dto.PublicationDto;
import com.serguzeo.StartSpring.models.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicationMapper {
    PublicationMapper INSTANCE = Mappers.getMapper(PublicationMapper.class);

    PublicationDto publicationToPublicationDto(Publication publication);
    Publication publicationDtoToPublication(PublicationDto publicationDto);
}
