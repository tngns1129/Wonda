package com.semo.wonda.repository;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.SharedGoal;
import com.semo.wonda.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SharedGoalRepository extends JpaRepository<SharedGoal, Long> {
    List<SharedGoal> findByGoal(GoalEntity goal);
    Optional<SharedGoal> findByGoalAndUser(GoalEntity goal, UserEntity user);
    Set<SharedGoal> findByUser(UserEntity user);
}
