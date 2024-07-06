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

    private String goalTitle;
    private String goalAmount;
    private String startDate;
    private String endDate;
    private GoalType goalType;
    private boolean completed;

    @Builder
    public GoalRequestDTO(String goalTitle, String goalAmount, String startDate, String endDate, GoalType goalType, boolean completed){
        this.goalTitle = goalTitle;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalType = goalType;
        this.completed = completed;
    }
}
