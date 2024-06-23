package com.semo.wonda.service;

import com.semo.wonda.data.mapper.GoalMapper;
import com.semo.wonda.data.mapper.RecodeMapper;
import com.semo.wonda.data.request.RecodeRequestDTO;
import com.semo.wonda.data.response.RecodeResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.RecodeEntity;
import com.semo.wonda.entity.UserEntity;
import com.semo.wonda.repository.GoalRepository;
import com.semo.wonda.repository.RecodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
public class RecodeService {

    @Autowired
    private final RecodeRepository recodeRepository;
    @Autowired
    private final GoalRepository goalRepository;

    public Page<RecodeResponseDTO> getRecodes(Pageable pageable, Long goalId){
        Optional<GoalEntity> optionalEntity = goalRepository.findById(goalId);
        if (optionalEntity.isPresent()) {
            GoalEntity goalEntity = optionalEntity.get();
            Page<RecodeEntity> entityPage = recodeRepository.findAllByGoalEntity(pageable, goalEntity);
            return RecodeMapper.INSTANCE.toDTOPage(entityPage);
        } else {
            // 엔티티가 존재하지 않을 경우 처리
            return null;
        }


    }

    public Map<String, Object> postRecode(RecodeRequestDTO requestDTO, Long goalId) {
        Map<String, Object> result = new HashMap<>();
        try{
            RecodeEntity entity = RecodeMapper.INSTANCE.toEntity(requestDTO);
            Optional<GoalEntity> optionalGoalEntity = goalRepository.findById(goalId);
            if(optionalGoalEntity.isPresent()){
                GoalEntity goalEntity = optionalGoalEntity.get();
                entity.setGoalEntity(goalEntity);
                recodeRepository.save(entity);
                result.put("code", 0);
                result.put("message", "Post recode success");
            }else {
                // 엔티티가 존재하지 않을 경우 처리
                result.put("code", 1);
                result.put("message", "goal is null");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
