package com.serguzeo.StartSpring.mappers;

import com.serguzeo.StartSpring.dto.PublicationDto;
import com.serguzeo.StartSpring.dto.PublicationWithUserDto;
import com.serguzeo.StartSpring.models.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicationWithUserMapper {
    PublicationWithUserMapper INSTANCE = Mappers.getMapper(PublicationWithUserMapper.class);

    PublicationWithUserDto publicationToPublicationDto(Publication publication);
    Publication publicationDtoToPublication(PublicationWithUserDto publicationWithUserDto);
}
