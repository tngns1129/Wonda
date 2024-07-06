package com.semo.wonda.data.mapper;

import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.data.response.UserResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.SharedGoal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface GoalMapper {
    GoalMapper INSTANCE = Mappers.getMapper(GoalMapper.class);

    @Mapping(source = "goalTitle", target = "goalTitle")
    @Mapping(source = "goalAmount", target = "goalAmount")
    @Mapping(source = "nowAmount", target = "nowAmount")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "goalType", target = "goalType")
    @Mapping(source = "completed", target = "completed")
    @Mapping(source = "shared", target = "shared")
    @Mapping(target = "sharedWithUsers", source = "sharedGoals")
    GoalResponseDTO toDTO(GoalEntity entity);

    List<GoalResponseDTO> toDTOList(List<GoalEntity> entities);

    default Page<GoalResponseDTO> toDTOPage(Page<GoalEntity> page) {
        return page.map(this::toDTO);
    }

    GoalEntity toEntity(GoalRequestDTO dto);

    @Mapping(target = "id", ignore = true)  // id는 업데이트 시 변경하지 않도록 무시
    void updateEntityFromDto(GoalRequestDTO dto, @MappingTarget GoalEntity entity);

    // SharedGoal을 UserResponseDTO로 매핑하는 메서드
    default Set<UserResponseDTO> mapSharedGoalsToUserResponseDTOs(Set<SharedGoal> sharedGoals) {
        return sharedGoals.stream()
                .map(sharedGoal -> UserMapper.INSTANCE.toDTO(sharedGoal.getUser()))
                .collect(Collectors.toSet());
    }
}
