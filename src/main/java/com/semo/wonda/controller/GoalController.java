package com.semo.wonda.controller;

import com.semo.wonda.data.request.GoalRequestDTO;
import com.semo.wonda.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private static final Logger logger = LoggerFactory.getLogger(GoalController.class);
    @Autowired
    private final GoalService goalService;

    //page default : page 0, size 20
    //ex)http://localhost:8080/goal?page=0&size=20&sort=createDate,DESC
    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity<?> getGoal(
            Pageable pageable
    ){
        try{
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success search");
            result.put("data", goalService.getGoal(pageable));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            logger.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity<?> postGoal(
            @RequestParam(value = "userName", required = true)String userName,
            @RequestBody GoalRequestDTO requestDTO){
        try {
            goalService.postGoal(userName, requestDTO);
            Map<String, Object> result = goalService.postGoal(userName, requestDTO);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            logger.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(method = DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteGoal(@RequestBody GoalRequestDTO requestDTO){
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("code",0);
            result.put("message","success save");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            logger.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @RequestMapping(value = "/all", method = GET)
    @ResponseBody
    public ResponseEntity<?> getAll(){
        try{
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success search");
            result.put("data", goalService.getAllGoal());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 처리 로직 추가
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("code",-1);
            errorResult.put("message","error occurred: " + e.getMessage());
            logger.error("Controller error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

}
