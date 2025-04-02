package com.semo.wonda.service;

import com.semo.wonda.data.mapper.RecordMapper;
import com.semo.wonda.data.request.RecordRequestDTO;
import com.semo.wonda.data.response.RecordResponseDTO;
import com.semo.wonda.entity.GoalEntity;
import com.semo.wonda.entity.RecordEntity;
import com.semo.wonda.repository.GoalRepository;
import com.semo.wonda.repository.RecordRepository;
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
public class RecordService {

    @Autowired
    private final RecordRepository recordRepository;
    @Autowired
    private final GoalRepository goalRepository;
    @Autowired
    private final UserRepository userRepository;

    public Page<RecordResponseDTO> getRecords(Pageable pageable, Long goalId){
        Optional<GoalEntity> optionalEntity = goalRepository.findById(goalId);
        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createDate").descending());
        }
        if (optionalEntity.isPresent()) {
            GoalEntity goalEntity = optionalEntity.get();
            Page<RecordEntity> entityPage = recordRepository.findAllByGoalEntityAndDeletedFalse(pageable, goalEntity);
            return RecordMapper.INSTANCE.toDTOPage(entityPage);
        } else {
            // 엔티티가 존재하지 않을 경우 처리
            return null;
        }


    }

    public Map<String, Object> postRecord(RecordRequestDTO requestDTO, Long goalId, String userName) {
        Map<String, Object> result = new HashMap<>();
        try{
            RecordEntity entity = RecordMapper.INSTANCE.toEntity(requestDTO);
            Optional<GoalEntity> optionalGoalEntity = goalRepository.findById(goalId);
            if(optionalGoalEntity.isPresent()){
                GoalEntity goalEntity = optionalGoalEntity.get();
                if(goalEntity.getUserEntity().equals(userRepository.findByUserName(userName))){
                    entity.setGoalEntity(goalEntity);
                    recordRepository.save(entity);
                    result.put("code", 0);
                    result.put("message", "Post record success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this goal and cannot post record");
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

    public Map<String, Object> deleteRecord(Long recordId, String userName) {
        Map<String, Object> result = new HashMap<>();
        try{
            Optional<RecordEntity> optionalRecordEntity = recordRepository.findById(recordId);
            if(optionalRecordEntity.isPresent()){
                RecordEntity recordEntity = optionalRecordEntity.get();
                if(recordEntity.getGoalEntity().getUserEntity().equals(userRepository.findByUserName(userName))) {
                    recordEntity.setDeleted(true);
                    result.put("code", 0);
                    result.put("message", "Delete record success");
                } else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this goal,record and cannot delete it");
                }

            }else {
                // 엔티티가 존재하지 않을 경우 처리
                result.put("code", 1);
                result.put("message", "record is null");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> updateRecord(RecordRequestDTO requestDTO, Long recordId, String userName) {
        Map<String, Object> result = new HashMap<>();
        try{
            Optional<RecordEntity> optionalRecordEntity = recordRepository.findById(recordId);
            if(optionalRecordEntity.isPresent()){
                RecordEntity recordEntity = optionalRecordEntity.get();
                if(recordEntity.getGoalEntity().getUserEntity().equals(userRepository.findByUserName(userName))){
                    RecordMapper.INSTANCE.updateEntityFromDto(requestDTO, recordEntity);
                    recordRepository.save(recordEntity);
                    result.put("code", 0);
                    result.put("message", "Update record success");
                }else{
                    result.put("code", 2);
                    result.put("message", "You are not the author of this record and cannot update it");
                }
            }else {
                // 엔티티가 존재하지 않을 경우 처리
                result.put("code", 1);
                result.put("message", "record is null");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
