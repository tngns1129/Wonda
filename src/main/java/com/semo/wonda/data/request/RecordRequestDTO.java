package com.semo.wonda.data.request;

import com.semo.wonda.entity.RecordType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RecordRequestDTO {
    private String recordTitle;
    private String recordAmount;
    private String recordContent;
    private RecordType recordType;
    private Date recordDate;

    @Builder
    public RecordRequestDTO(String recordTitle, String recordAmount, String recordContent, RecordType recordType, Date recordDate){
        this.recordTitle = recordTitle;
        this.recordAmount = recordAmount;
        this.recordContent = recordContent;
        this.recordType = recordType;
        this.recordDate = recordDate;
    }
}
