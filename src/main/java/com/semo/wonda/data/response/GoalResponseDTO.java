package com.semo.wonda.data.response;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import lombok.Builder;
import lombok.Data;

@Data
public class GoalResponseDTO {
    private String goalTitle;
    private String goalAmount;
    private String startDate;
    private String endDate;
    private GoalType goalType;

    public GoalResponseDTO(String goalTitle, String goalAmount, String startDate, String endDate, GoalType goalType){
        this.goalTitle = goalTitle;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.goalType = goalType;
    }

    // 기본 생성자
    public GoalResponseDTO() {}

}