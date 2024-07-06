package com.semo.wonda.repository;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

    Page<GoalEntity> findAllByUserEntityAndDeletedFalse(Pageable pageable, UserEntity user);

    Page<GoalEntity> findAllByUserEntityAndGoalType(Pageable pageable, UserEntity user, GoalType goalType);

    List<GoalEntity> findAllByUserEntity(UserEntity user);

    Page<GoalEntity> findAllByUserEntityAndGoalTypeAndDeletedFalse(Pageable pageable, UserEntity user, GoalType goalType);

    Page<GoalEntity> findAllByUserEntityAndDeletedFalseAndCompleted(Pageable pageable, UserEntity user, boolean completed);
    Page<GoalEntity> findAllByUserEntityAndDeletedFalseAndCompletedAndGoalType(Pageable pageable, UserEntity user, boolean completed, GoalType goalType);

    Page<GoalEntity> findAllByUserEntityAndGoalTypeAndDeletedFalseAndCompletedFalse(Pageable pageable, UserEntity user, GoalType goalType);

    // 본인 아이디로 공유된 Goal도 함께 조회하는 메서드들
    Page<GoalEntity> findAllByUserEntityAndDeletedFalseAndCompletedAndGoalTypeOrIdIn(Pageable pageable, UserEntity user, Boolean completed, GoalType goalType, Set<Long> sharedGoalIds);

    Page<GoalEntity> findAllByUserEntityAndDeletedFalseAndCompletedOrIdIn(Pageable pageable, UserEntity user, Boolean completed, Set<Long> sharedGoalIds);

    Page<GoalEntity> findAllByUserEntityAndGoalTypeAndDeletedFalseOrIdIn(Pageable pageable, UserEntity user, GoalType goalType, Set<Long> sharedGoalIds);

    Page<GoalEntity> findAllByUserEntityAndDeletedFalseOrIdIn(Pageable pageable, UserEntity user, Set<Long> sharedGoalIds);
}
