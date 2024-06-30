package com.semo.wonda.service;

import com.semo.wonda.data.mapper.GoalMapper;
import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.UserEntity;
import com.semo.wonda.repository.GoalRepository;
import com.semo.wonda.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class GoalService {

    @Autowired
    private final GoalRepository goalRepository;
    @Autowired
    private final UserRepository userRepository;

    public List<GoalResponseDTO> getAllGoalByUser(String username){
        UserEntity user = userRepository.findByUserName(username);
        List<GoalEntity> entityList = goalRepository.findAllByUserEntity(user);
        return GoalMapper.INSTANCE.toDTOList(entityList);
    }

    public Page<GoalResponseDTO> getGoal(Pageable pageable){
        Page<GoalEntity> entityPage = goalRepository.findAll(pageable);
        return GoalMapper.INSTANCE.toDTOPage(entityPage);
    }

    public Page<GoalResponseDTO> getGoalByUsernameAndGoalType(Pageable pageable, String userName, GoalType goalType){
        UserEntity user = userRepository.findByUserName(userName);
        Page<GoalEntity> entityPage = Page.empty();
        if(user != null){
            if (!pageable.getSort().isSorted()) {
                pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("updateDate").descending());
            }

            if(goalType == null){
                entityPage = goalRepository.findAllByUserEntityAndDeletedFalse(pageable, user);
            }else{
                entityPage = goalRepository.findAllByUserEntityAndGoalTypeAndDeletedFalse(pageable, user, goalType);
            }

        }else {
        }

        return GoalMapper.INSTANCE.toDTOPage(entityPage);
    }

    public HashMap<String, Object> postGoal(String userName, GoalRequestDTO requestDTO){
        HashMap<String, Object> result = new HashMap<>();
        try{
            GoalEntity entity = GoalMapper.INSTANCE.toEntity(requestDTO);
            if(userName.equals("anonymousUser")){
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

}
