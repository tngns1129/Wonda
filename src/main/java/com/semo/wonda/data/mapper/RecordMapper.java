package com.semo.wonda.data.mapper;

import com.semo.wonda.data.request.RecordRequestDTO;
import com.semo.wonda.data.response.RecordResponseDTO;
import com.semo.wonda.entity.RecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface RecordMapper {
    RecordMapper INSTANCE = Mappers.getMapper(RecordMapper.class);

    @Mapping(source = "recordTitle", target = "recordTitle")
    @Mapping(source = "recordAmount", target = "recordAmount")
    @Mapping(source = "recordContent", target = "recordContent")
    @Mapping(source = "recordType", target = "recordType")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "updateDate", target = "updateDate")
    RecordResponseDTO toDTO(RecordEntity entity);

    List<RecordResponseDTO> toDTOList(List<RecordEntity> entities);

    default Page<RecordResponseDTO> toDTOPage(Page<RecordEntity> page) {
        return page.map(this::toDTO);
    }

    RecordEntity toEntity(RecordRequestDTO dto);

    @Mapping(target = "id", ignore = true)  // id는 업데이트 시 변경하지 않도록 무시
    void updateEntityFromDto(RecordRequestDTO dto, @MappingTarget RecordEntity entity);
}
