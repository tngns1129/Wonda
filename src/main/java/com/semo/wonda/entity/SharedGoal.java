package com.semo.wonda.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Entity(name = "sharedGoal")
public class SharedGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private GoalEntity goal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public SharedGoal(GoalEntity goal, UserEntity user) {
        this.goal = goal;
        this.user = user;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SharedGoal)) return false;
        SharedGoal that = (SharedGoal) o;
        return Objects.equals(goal, that.goal) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goal, user);
    }
}
