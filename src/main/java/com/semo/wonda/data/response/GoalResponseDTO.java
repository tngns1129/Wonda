package com.semo.wonda.data.response;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.SharedGoal;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class GoalResponseDTO {
    private Long id;
    private String goalTitle;
    private String goalAmount;
    private String nowAmount;
    private String startDate;
    private String endDate;
    private GoalType goalType;
    private boolean completed;
    private boolean shared;
    private Set<UserResponseDTO> sharedWithUsers;

//    public GoalResponseDTO(Long id, String goalTitle, String goalAmount, String startDate, String endDate, GoalType goalType){
//        this.id = id;
//        this.goalTitle = goalTitle;
//        this.goalAmount = goalAmount;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.goalType = goalType;
//    }
}