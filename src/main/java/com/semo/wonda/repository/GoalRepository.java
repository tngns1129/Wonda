package com.semo.wonda.repository;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

    Page<GoalEntity> findAllByUserEntity(Pageable pageable, UserEntity user);

    Page<GoalEntity> findAllByUserEntityAndGoalType(Pageable pageable, UserEntity user, GoalType goalType);

}
