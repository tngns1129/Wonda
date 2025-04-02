package com.semo.wonda.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<?> home() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 0);
        result.put("message", "Hi I'm wonda");

        //return "index.html";
        return ResponseEntity.ok(result);
    }

}