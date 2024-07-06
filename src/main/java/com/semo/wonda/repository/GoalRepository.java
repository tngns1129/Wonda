package com.semo.wonda.repository;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

    Page<GoalEntity> findAllByUserEntityAndDeletedFalse(Pageable pageable, UserEntity user);

    Page<GoalEntity> findAllByUserEntityAndGoalType(Pageable pageable, UserEntity user, GoalType goalType);

    List<GoalEntity> findAllByUserEntity(UserEntity user);

    Page<GoalEntity> findAllByUserEntityAndGoalTypeAndDeletedFalse(Pageable pageable, UserEntity user, GoalType goalType);

    Page<GoalEntity> findAllByUserEntityAndDeletedFalseAndCompleted(Pageable pageable, UserEntity user, boolean completed);
    Page<GoalEntity> findAllByUserEntityAndDeletedFalseAndCompletedAndGoalType(Pageable pageable, UserEntity user, boolean completed, GoalType goalType);

    Page<GoalEntity> findAllByUserEntityAndGoalTypeAndDeletedFalseAndCompletedFalse(Pageable pageable, UserEntity user, GoalType goalType);
}
