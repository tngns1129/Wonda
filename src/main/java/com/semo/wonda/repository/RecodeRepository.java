package com.semo.wonda.repository;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.RecodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecodeRepository extends JpaRepository<RecodeEntity, Long> {
    Page<RecodeEntity> findAllByGoalEntity(Pageable pageable, GoalEntity goalEntity);
}
