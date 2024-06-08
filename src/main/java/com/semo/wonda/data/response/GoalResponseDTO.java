package com.semo.wonda.data.response;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import lombok.Builder;
import lombok.Data;

@Data
public class GoalResponseDTO {
    private String goalName;
    private String content;
    private GoalType goalType;

    public GoalResponseDTO(String goalName, String content, GoalType goalType){
        this.goalName = goalName;
        this.content = content;
        this.goalType = goalType;
    }

    // 기본 생성자
    public GoalResponseDTO() {}

}