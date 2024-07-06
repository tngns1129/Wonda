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
import com.semo.wonda.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private final UserRepository userRepository;

    public Page<RecodeResponseDTO> getRecodes(Pageable pageable, Long goalId){
        Optional<GoalEntity> optionalEntity = goalRepository.findById(goalId);
        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate").descending());
        }
        if (optionalEntity.isPresent()) {
            GoalEntity goalEntity = optionalEntity.get();
            Page<RecodeEntity> entityPage = recodeRepository.findAllByGoalEntityAndDeletedFalse(pageable, goalEntity);
            return RecodeMapper.INSTANCE.toDTOPage(entityPage);
        } else {
            // 엔티티가 존재하지 않을 경우 처리
            return null;
        }


    }

    public Map<String, Object> postRecode(RecodeRequestDTO requestDTO, Long goalId, String userName) {
        Map<String, Object> result = new HashMap<>();
        try{
            RecodeEntity entity = RecodeMapper.INSTANCE.toEntity(requestDTO);
            Optional<GoalEntity> optionalGoalEntity = goalRepository.findById(goalId);
            if(optionalGoalEntity.isPresent()){
                GoalEntity goalEntity = optionalGoalEntity.get();
                if(goalEntity.getUserEntity().equals(userRepository.findByUserName(userName))){
                    entity.setGoalEntity(goalEntity);
                    recodeRepository.save(entity);
                    result.put("code", 0);
                    result.put("message", "Post recode success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this goal and cannot post recode");
                }

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

    public Map<String, Object> deleteRecode(Long recodeId, String userName) {
        Map<String, Object> result = new HashMap<>();
        try{
            Optional<RecodeEntity> optionalRecodeEntity = recodeRepository.findById(recodeId);
            if(optionalRecodeEntity.isPresent()){
                RecodeEntity recodeEntity = optionalRecodeEntity.get();
                if(recodeEntity.getGoalEntity().getUserEntity().equals(userRepository.findByUserName(userName))) {
                    recodeEntity.setDeleted(true);
                    result.put("code", 0);
                    result.put("message", "Delete recode success");
                } else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this goal,recode and cannot delete it");
                }

            }else {
                // 엔티티가 존재하지 않을 경우 처리
                result.put("code", 1);
                result.put("message", "recode is null");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> updateRecode(RecodeRequestDTO requestDTO, Long recodeId, String userName) {
        Map<String, Object> result = new HashMap<>();
        try{
            Optional<RecodeEntity> optionalRecodeEntity = recodeRepository.findById(recodeId);
            if(optionalRecodeEntity.isPresent()){
                RecodeEntity recodeEntity = optionalRecodeEntity.get();
                if(recodeEntity.getGoalEntity().getUserEntity().equals(userRepository.findByUserName(userName))){
                    RecodeMapper.INSTANCE.updateEntityFromDto(requestDTO, recodeEntity);
                    recodeRepository.save(recodeEntity);
                    result.put("code", 0);
                    result.put("message", "Update recode success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this recode and cannot update it");
                }
            }else {
                // 엔티티가 존재하지 않을 경우 처리
                result.put("code", 1);
                result.put("message", "recode is null");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
