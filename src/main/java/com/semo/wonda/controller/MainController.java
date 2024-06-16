package com.semo.wonda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {


        return "index.html";
    }

}