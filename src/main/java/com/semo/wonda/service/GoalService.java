package com.semo.wonda.service;

import com.semo.wonda.data.mapper.GoalMapper;
import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.SharedGoal;
import com.semo.wonda.entity.UserEntity;
import com.semo.wonda.repository.GoalRepository;
import com.semo.wonda.repository.SharedGoalRepository;
import com.semo.wonda.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GoalService {

    @Autowired
    private final GoalRepository goalRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final SharedGoalRepository sharedGoalRepository;

    public List<GoalResponseDTO> getAllGoalByUser(String username){
        UserEntity user = userRepository.findByUserName(username);
        List<GoalEntity> entityList = goalRepository.findAllByUserEntity(user);
        return GoalMapper.INSTANCE.toDTOList(entityList);
    }

    public Page<GoalResponseDTO> getGoal(Pageable pageable){
        Page<GoalEntity> entityPage = goalRepository.findAll(pageable);
        return GoalMapper.INSTANCE.toDTOPage(entityPage);
    }

    public Page<GoalResponseDTO> getGoalByUsernameAndGoalTypeAndCompleted(Pageable pageable, String userName, GoalType goalType, Boolean completed){
        UserEntity user = userRepository.findByUserName(userName);
        Page<GoalEntity> entityPage = Page.empty();
        if (user != null) {
            // 공유된 Goal의 ID를 모아둘 Set
            Set<Long> sharedGoalIds = new HashSet<>();

            // 본인 아이디로 공유된 Goal의 ID들을 가져옴
            Set<SharedGoal> sharedGoals = sharedGoalRepository.findByUser(user);
            sharedGoalIds.addAll(sharedGoals.stream().map(sharedGoal -> sharedGoal.getGoal().getId()).collect(Collectors.toSet()));

            // Goal을 가져올 때 본인 아이디로 공유된 Goal도 함께 불러오기 위해 추가
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate").descending());
            }

            if (goalType != null && completed != null) {
                entityPage = goalRepository.findAllByUserEntityAndDeletedFalseAndCompletedAndGoalTypeOrIdIn(pageable, user, completed, goalType, sharedGoalIds);
            } else if (completed != null) {
                entityPage = goalRepository.findAllByUserEntityAndDeletedFalseAndCompletedOrIdIn(pageable, user, completed, sharedGoalIds);
            } else if (goalType != null) {
                entityPage = goalRepository.findAllByUserEntityAndGoalTypeAndDeletedFalseOrIdIn(pageable, user, goalType, sharedGoalIds);
            } else {
                entityPage = goalRepository.findAllByUserEntityAndDeletedFalseOrIdIn(pageable, user, sharedGoalIds);
            }
        }

        return GoalMapper.INSTANCE.toDTOPage(entityPage);
    }

    public HashMap<String, Object> postGoal(String userName, GoalRequestDTO requestDTO){
        HashMap<String, Object> result = new HashMap<>();
        try{
            GoalEntity entity = GoalMapper.INSTANCE.toEntity(requestDTO);
            if(userName == null){
                //todo: 비로그인 사용자
            }
            UserEntity user = userRepository.findByUserName(userName);
            if(user == null){
                result.put("code", 1);
                result.put("message", "There is not user");
            }else{
                result.put("code", 0);
                result.put("message", "Post goal success");
                entity.setUserEntity(user);
                goalRepository.save(entity);
            }

        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 2);
            result.put("message", "An error occurred while updating the goal");
        }
        return result;
    }

    public HashMap<String, Object> deleteGoal(Long goalId, String userName){
        HashMap<String, Object> result = new HashMap<>();
        try{
            Optional<GoalEntity> optionalEntity = goalRepository.findById(goalId);
            if (optionalEntity.isPresent()) {
                GoalEntity goalEntity = optionalEntity.get();
                // Use goalEntity
                if(goalEntity.getUserEntity().equals(userRepository.findByUserName(userName))){
                    goalEntity.setDeleted(true);
                    result.put("code", 0);
                    result.put("message", "Delete goal Success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this goal and cannot delete it");
                }
            } else {
                // Handle the case where the entity is not found
                result.put("code", 1);
                result.put("message", "There is not goal");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", -1);
            result.put("message", "An error occurred while updating the goal");
        }
        return result;
    }

    public HashMap<String, Object> updateGoal(Long goalId, String userName, GoalRequestDTO goalRequestDTO){
        HashMap<String, Object> result = new HashMap<>();
        try{
            Optional<GoalEntity> optionalEntity = goalRepository.findById(goalId);
            if (optionalEntity.isPresent()) {
                GoalEntity goalEntity = optionalEntity.get();
                // Use goalEntity
                if(goalEntity.getUserEntity().equals(userRepository.findByUserName(userName))){
                    GoalMapper.INSTANCE.updateEntityFromDto(goalRequestDTO, goalEntity);
                    goalRepository.save(goalEntity);
                    result.put("code", 0);
                    result.put("message", "Update Success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this goal and cannot update it");
                }
            } else {
                // Handle the case where the entity is not found
                result.put("code", 1);
                result.put("message", "There is not goal");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("code", 3);
            result.put("message", "An error occurred while updating the goal");
        }
        return result;
    }

    @Transactional
    public HashMap<String, Object> shareGoal(Long goalId, Set<String> userNames) {
        HashMap<String, Object> result = new HashMap<>();
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found with id: " + goalId));
        goal.setShared(true);

        for (String userName : userNames) {
            UserEntity user = userRepository.findByUserName(userName);

            // 이미 존재하는 공유 목표인지 검사
            Optional<SharedGoal> existingSharedGoal = sharedGoalRepository.findByGoalAndUser(goal, user);
            if (existingSharedGoal.isPresent()) {
                // 이미 존재하면 추가하지 않음
                continue;
            }

            SharedGoal sharedGoal = new SharedGoal();
            sharedGoal.setGoal(goal);
            sharedGoal.setUser(user);

            goal.getSharedGoals().add(sharedGoal);

            sharedGoalRepository.save(sharedGoal);
        }
        Set<SharedGoal> remainingUsers = goal.getSharedGoals();
        if(!remainingUsers.isEmpty()){
            goal.setShared(true);
        }
        goalRepository.save(goal);
        result.put("code", 0);
        result.put("message", "Success Share");
        return result;
    }

    public Map<String, Object> unshareGoal(Long goalId, Set<String> userNames) {
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));

        Set<UserEntity> usersToUnshare = userRepository.findByUserNameIn(userNames);
        if (usersToUnshare.isEmpty()) {
            throw new EntityNotFoundException("Users not found");
        }
        Set<SharedGoal> sharedGoalsToRemove = goal.getSharedGoals().stream()
                .filter(sharedGoal -> usersToUnshare.contains(sharedGoal.getUser()))
                .collect(Collectors.toSet());

        goal.getSharedGoals().removeAll(sharedGoalsToRemove);
        Set<SharedGoal> remainingUsers = goal.getSharedGoals();
        if(remainingUsers.isEmpty()){
            goal.setShared(false);
        }
        goalRepository.save(goal);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("code", 0);
        return result;
    }

    public boolean isOwner(Long goalId, String username) {
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Goal not found"));
        return goal.getUserEntity().getUserName().equals(username);
    }

}
