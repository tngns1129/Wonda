package com.semo.wonda.controller;

import com.semo.wonda.SecurityUtils;
import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.entity.UserEntity;
import com.semo.wonda.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/****
 * 목표 목록
 * /goal    GET POST
 * /all     GET
 */
@Controller
@RequestMapping(value = "/goal")
@RequiredArgsConstructor
public class GoalController {

    private static final Logger log = LoggerFactory.getLogger(GoalController.class);
    @Autowired
    private final GoalService goalService;

    //page default : page 0, size 20
    //ex)http://localhost:8080/goal?page=0&size=20&sort=createDate,DESC
    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity<?> getGoals(
            Pageable pageable,
            @RequestParam(required = false) GoalType goalType,
            @RequestParam(required = false) Boolean completed
    ){
        try{
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success search");
            result.put("data", goalService.getGoalByUsernameAndGoalTypeAndCompleted(pageable, SecurityUtils.getCurrentUsername(), goalType, completed));
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity<?> postGoal(
            @RequestBody GoalRequestDTO requestDTO){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = goalService.postGoal(userName, requestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(method = DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteGoal(
            @RequestParam Long goalId
    ){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = goalService.deleteGoal(goalId, userName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }
    @RequestMapping(method = PUT)
    @ResponseBody
    public ResponseEntity<?> updateGoal(
            @RequestBody GoalRequestDTO requestDTO,
            @RequestParam Long goalId
    ){
        try {
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = goalService.updateGoal(goalId, userName, requestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(value = "/share", method = POST)
    @ResponseBody
    public ResponseEntity<?> shareGoals(
            @RequestParam Long goalId,
            @RequestBody Map<String, Set<String>> sharedWithUsers
    ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            //본인글인지 확인
            if (!goalService.isOwner(goalId, userName)) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("code", 1);
                errorResult.put("message", "You do not have permission to unshare this goal.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResult);
            }
            Map<String, Object> result = goalService.shareGoal(goalId, sharedWithUsers.get("sharedWithUsers"));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(value = "/share", method = DELETE)
    @ResponseBody
    public ResponseEntity<?> unshareGoals(
            @RequestParam Long goalId,
            @RequestBody Map<String, Set<String>> sharedWithUsers
    ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            //본인글인지 확인
            if (!goalService.isOwner(goalId, userName)) {
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("code", 1);
                errorResult.put("message", "You do not have permission to unshare this goal.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResult);
            }
            Map<String, Object> result = goalService.unshareGoal(goalId, sharedWithUsers.get("unsharedWithUsers"));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(value = "/all", method = GET)
    @ResponseBody
    public ResponseEntity<?> getAllGoals(
            Pageable pageable
    ){
        try{
            String userName = SecurityUtils.getCurrentUsername();
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success search");
            result.put("data", goalService.getAllGoalByUser(userName));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            log.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

}
