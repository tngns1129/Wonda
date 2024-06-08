package com.semo.wonda.data.request;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalRequestDTO {

    private String goalName;
    private String content;
    private GoalType goalType;

    @Builder
    public GoalRequestDTO(String goalName, String content, GoalType goalType){
        this.goalName = goalName;
        this.content = content;
        this.goalType = goalType;
    }
}
