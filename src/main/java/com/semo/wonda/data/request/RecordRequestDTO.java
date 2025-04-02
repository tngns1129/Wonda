package com.semo.wonda.data.request;

import com.semo.wonda.entity.RecordType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordRequestDTO {
    private String recordTitle;
    private String recordAmount;
    private String recordContent;
    private RecordType recordType;

    @Builder
    public RecordRequestDTO(String recordTitle, String recordAmount, String recordContent, RecordType recordType){
        this.recordTitle = recordTitle;
        this.recordAmount = recordAmount;
        this.recordContent = recordContent;
        this.recordType = recordType;
    }
}
