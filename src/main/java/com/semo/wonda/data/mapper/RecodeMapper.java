package com.semo.wonda.data.mapper;

import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.request.RecodeRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.data.response.RecodeResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.RecodeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface RecodeMapper {
    RecodeMapper INSTANCE = Mappers.getMapper(RecodeMapper.class);

    @Mapping(source = "recodeTitle", target = "recodeTitle")
    @Mapping(source = "recodeAmount", target = "recodeAmount")
    @Mapping(source = "recodeContent", target = "recodeContent")
    @Mapping(source = "recodeType", target = "recodeType")
    RecodeResponseDTO toDTO(RecodeEntity entity);

    List<RecodeResponseDTO> toDTOList(List<RecodeEntity> entities);

    default Page<RecodeResponseDTO> toDTOPage(Page<RecodeEntity> page) {
        return page.map(this::toDTO);
    }

    RecodeEntity toEntity(RecodeRequestDTO dto);

    @Mapping(target = "id", ignore = true)  // id는 업데이트 시 변경하지 않도록 무시
    void updateEntityFromDto(RecodeRequestDTO dto, @MappingTarget RecodeEntity entity);
}
