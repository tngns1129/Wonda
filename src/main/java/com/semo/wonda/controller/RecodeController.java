package com.semo.wonda.controller;

import com.semo.wonda.SecurityUtils;
import com.semo.wonda.data.request.RecodeRequestDTO;
import com.semo.wonda.entity.GoalType;
import com.semo.wonda.service.RecodeService;
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

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/recode")
@RequiredArgsConstructor
public class RecodeController {
    private static final Logger log = LoggerFactory.getLogger(RecodeController.class);

    @Autowired
    RecodeService recodeService;
    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity<?> getRecode(
            Pageable pageable,
            @RequestParam(required = true) Long goalId
    ){
        try{
            Map<String, Object> result = new HashMap<>();
            result.put("code", 0);
            result.put("message", "success search");
            result.put("data", recodeService.getRecodes(pageable, goalId));
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
    public  ResponseEntity<?> postRecode(
            @RequestBody RecodeRequestDTO requestDTO,
            @RequestParam(required = true) Long goalId
            ){
        try{
            Map<String, Object> result = recodeService.postRecode(requestDTO, goalId);
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

}
