package com.semo.wonda.repository;

import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserName(String id);
    Set<UserEntity> findByUserNameIn(Set<String> usernames);

}
