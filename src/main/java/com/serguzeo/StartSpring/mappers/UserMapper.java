package com.serguzeo.StartSpring.mappers;

import com.serguzeo.StartSpring.dto.UserDto;
import com.serguzeo.StartSpring.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userEntityToUserDto(UserEntity userEntity);
    UserEntity userDtoToUserEntity(UserDto userDto);
}
