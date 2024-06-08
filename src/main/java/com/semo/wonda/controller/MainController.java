package com.semo.wonda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = "userId", required = false) Long userId, Model model) {

        if(userId == null) {
            System.out.println("로그인 하지 않음");
            model.addAttribute("userId", "not login");
        }
        else {
            System.out.println("로그인 유저의 Id : " + userId);
            model.addAttribute("userId", userId);
        }
        return "index.html";
    }

}