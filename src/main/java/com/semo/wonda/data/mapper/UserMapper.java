package com.semo.wonda.data.mapper;

import com.semo.wonda.data.request.UserRequestDTO;
import com.semo.wonda.data.response.UserResponseDTO;
import com.semo.wonda.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "userName", target = "userName")
    UserResponseDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserRequestDTO dto);
}
