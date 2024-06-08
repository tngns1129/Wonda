package com.semo.wonda.controller;

import com.semo.wonda.data.request.UserRequestDTO;
import com.semo.wonda.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/sign")
public class SignController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/in", method = POST)
    @ResponseBody
    public ResponseEntity<?> signin(HttpServletRequest request){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUserName(request.getParameter("userName"));
        userRequestDTO.setUserPassward(request.getParameter("userPassward"));
        Map<String, Object> result = userService.signIn(userRequestDTO);
        if ((int) result.get("code") == 0) {
            HttpSession session = request.getSession();
            session.setAttribute("loginMember", userRequestDTO);
            session.setMaxInactiveInterval(60 * 30);
            request.setAttribute("user", userRequestDTO.getUserName());
            result.put("message", "로그인 성공");
        } else {
            result.put("message", "로그인 실패");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/up",method = POST)
    @ResponseBody
    public ResponseEntity<?> signup(
            @RequestBody UserRequestDTO userRequestDTO){
        Map<String, Object> result = userService.checkId(userRequestDTO.getUserName());
        if (result.get("code").equals(0)) {
            try {
                userService.signUp(userRequestDTO);
                result.put("message", "아이디 생성 완료");
            } catch (Exception e) {
                result.put("code", -1);
                result.put("message", e.getMessage());
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/status", method = GET)
    @ResponseBody
    public ResponseEntity<?> status(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String user = (String) session.getAttribute("user");
        if (user != null) {
            result.put("message", "로그인 상태");
            result.put("user", user);
        } else {
            result.put("message", "로그아웃 상태");
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
