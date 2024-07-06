package com.semo.wonda.data.response;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoalResponseDTO {
    private Long id;
    private String goalTitle;
    private String goalAmount;
    private String nowAmout;
    private String startDate;
    private String endDate;
    private GoalType goalType;
    private boolean completed;

//    public GoalResponseDTO(Long id, String goalTitle, String goalAmount, String startDate, String endDate, GoalType goalType){
//        this.id = id;
//        this.goalTitle = goalTitle;
//        this.goalAmount = goalAmount;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.goalType = goalType;
//    }
}