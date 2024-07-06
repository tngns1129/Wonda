package com.semo.wonda.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "goal")
public class GoalEntity extends BasicEntity {

    //목표명
    @Column(name = "goal_title")
    private String goalTitle;

    //목표금액
    @Column(name = "goal_amount")
    private String goalAmount;

    //시작일
    @Column(name = "start_date")
    private String startDate;

    //목표일
    @Column(name = "end_date")
    private String endDate;

    //목표 타입 (save, spend)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "goal_type")
    private GoalType goalType;

    //완료 여부
    @Column(name = "completed")
    private boolean completed = false;

    //목표작성자
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

//    @Builder
//    public GoalEntity(String goalTitle, String goalAmount, String startDate, String endDate, GoalType goalType, UserEntity userEntity){
//        this.goalTitle = goalTitle;
//        this.goalAmount = goalAmount;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.goalType = goalType;
//        this.userEntity = userEntity;
//    }

}
