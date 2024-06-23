package com.semo.wonda.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "recode")
public class RecodeEntity extends BasicEntity{

    //기록명
    @Column(name = "recode_title")
    private String recodeTitle;

    //기록금액
    @Column(name = "recode_amount")
    private String recodeAmount;

    //기록내용
    @Column(name = "recode_content")
    private String recodeContent;

    //기록 타입 (income, expenses)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "recode_type")
    private RecodeType recodeType;

    //목표
    @ManyToOne
    @JoinColumn(name = "goal_id", nullable = false)
    private GoalEntity goalEntity;

    @Builder
    public RecodeEntity(String recodeTitle, String recodeAmount, String recodeContent, RecodeType recodeType, GoalEntity goalEntity){
        this.recodeTitle = recodeTitle;
        this.recodeAmount = recodeAmount;
        this.recodeContent = recodeContent;
        this.recodeType = recodeType;
        this.goalEntity = goalEntity;
    }
}
