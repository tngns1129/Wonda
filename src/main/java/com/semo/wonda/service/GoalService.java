package com.semo.wonda.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.semo.wonda.data.GoalMapper;
import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.data.response.GoalResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.UserEntity;
import com.semo.wonda.repository.GoalRepository;
import com.semo.wonda.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class GoalService {

    @Autowired
    private final GoalRepository goalRepository;
    @Autowired
    private final UserRepository userRepository;

    public List<GoalResponseDTO> getAllGoal(){
        List<GoalEntity> entityList = goalRepository.findAll();
        return GoalMapper.INSTANCE.toDTOList(entityList);
    }

    public Page<GoalResponseDTO> getGoal(Pageable pageable){
        Page<GoalEntity> entityPage = goalRepository.findAll(pageable);
        return GoalMapper.INSTANCE.toDTOPage(entityPage);
    }

    public Page<GoalResponseDTO> getGoalByUsername(Pageable pageable, String userName){
        UserEntity user = userRepository.findByUserName(userName);
        Page<GoalEntity> entityPage = null;
        if(user != null){
            entityPage = goalRepository.findAllByUserEntity(pageable, user);
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
        }
        return result;
    }

}
