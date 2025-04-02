package com.semo.wonda.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "record")
public class RecordEntity extends BasicEntity{

    //기록명
    @Column(name = "record_title")
    private String recordTitle;

    //기록금액
    @Column(name = "record_amount")
    private String recordAmount;

    //기록내용
    @Column(name = "record_content")
    private String recordContent;

    //기록 타입 (income, expenses)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "record_type")
    private RecordType recordType;

    //목표
    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private GoalEntity goalEntity;

    @Builder
    public RecordEntity(String recordTitle, String recordAmount, String recordContent, RecordType recordType, GoalEntity goalEntity){
        this.recordTitle = recordTitle;
        this.recordAmount = recordAmount;
        this.recordContent = recordContent;
        this.recordType = recordType;
        this.goalEntity = goalEntity;
    }
}
