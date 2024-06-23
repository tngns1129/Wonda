package com.semo.wonda.data.request;

import com.semo.wonda.data.response.RecodeResponseDTO;
import com.semo.wonda.entity.RecodeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecodeRequestDTO {
    private String recodeTitle;
    private String recodeAmount;
    private String recodeContent;
    private RecodeType recodeType;

    @Builder
    public RecodeRequestDTO(String recodeTitle, String recodeAmount, String recodeContent, RecodeType recodeType){
        this.recodeTitle = recodeTitle;
        this.recodeAmount = recodeAmount;
        this.recodeContent = recodeContent;
        this.recodeType = recodeType;
    }
}
