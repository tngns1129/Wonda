package com.semo.wonda.data;

import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.request.UserRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.data.response.UserResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "userPassward", target = "userPassward")
    UserResponseDTO toDTO(UserEntity entity);

    UserEntity toEntity(UserRequestDTO dto);
}
