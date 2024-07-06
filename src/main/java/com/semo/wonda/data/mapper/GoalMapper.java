package com.semo.wonda.data.mapper;

import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface GoalMapper {
    GoalMapper INSTANCE = Mappers.getMapper(GoalMapper.class);

    @Mapping(source = "goalTitle", target = "goalTitle")
    @Mapping(source = "goalAmount", target = "goalAmount")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "goalType", target = "goalType")
    @Mapping(source = "completed", target = "completed")
    GoalResponseDTO toDTO(GoalEntity entity);

    List<GoalResponseDTO> toDTOList(List<GoalEntity> entities);

    default Page<GoalResponseDTO> toDTOPage(Page<GoalEntity> page) {
        return page.map(this::toDTO);
    }

    GoalEntity toEntity(GoalRequestDTO dto);

    @Mapping(target = "id", ignore = true)  // id는 업데이트 시 변경하지 않도록 무시
    void updateEntityFromDto(GoalRequestDTO dto, @MappingTarget GoalEntity entity);

}
