package com.semo.wonda.data.response;

import com.semo.wonda.entity.RecordType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordResponseDTO {
    private Long id;
    private String recordTitle;
    private String recordAmount;
    private String recordContent;
    private RecordType recordType;
    private Date recordDate;
    private Date createDate;
    private Date updateDate;

    @Builder
    public RecordResponseDTO(String recordTitle, String recordAmount, String recordContent, RecordType recordType, Date createDate, Date updateDate){
        this.recordTitle = recordTitle;
        this.recordAmount = recordAmount;
        this.recordContent = recordContent;
        this.recordType = recordType;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}