package com.semo.wonda.data;

import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface GoalMapper {
    GoalMapper INSTANCE = Mappers.getMapper(GoalMapper.class);

    @Mapping(source = "goalName", target = "goalName")
    @Mapping(source = "goalType", target = "goalType")
    GoalResponseDTO toDTO(GoalEntity entity);

    List<GoalResponseDTO> toDTOList(List<GoalEntity> entities);

    default Page<GoalResponseDTO> toDTOPage(Page<GoalEntity> page) {
        return page.map(this::toDTO);
    }

    GoalEntity toEntity(GoalRequestDTO dto);

}
