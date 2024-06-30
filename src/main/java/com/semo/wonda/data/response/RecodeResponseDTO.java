package com.semo.wonda.data.response;

import com.semo.wonda.entity.RecodeType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecodeResponseDTO {
    private Long id;
    private String recodeTitle;
    private String recodeAmount;
    private String recodeContent;
    private RecodeType recodeType;

    @Builder
    public RecodeResponseDTO(String recodeTitle, String recodeAmount, String recodeContent, RecodeType recodeType){
        this.recodeTitle = recodeTitle;
        this.recodeAmount = recodeAmount;
        this.recodeContent = recodeContent;
        this.recodeType = recodeType;
    }
}