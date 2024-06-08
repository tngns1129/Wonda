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

    @Column(name = "goal_name")
    private String goalName;

    @Column(name = "content")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "goal_type")
    private GoalType goalType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Builder
    public GoalEntity(String goalName, String content, GoalType goalType, UserEntity userEntity){
        this.goalName = goalName;
        this.content = content;
        this.goalType = goalType;
        this.userEntity = userEntity;
    }

}
