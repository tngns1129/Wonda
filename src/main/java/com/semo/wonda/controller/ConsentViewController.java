package com.semo.wonda.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller  // ❌ @RestController 대신 @Controller 사용
@RequestMapping("/consents/view")
public class ConsentViewController {

    @GetMapping
    public String showConsentPage() {
        return "consent";  // templates/consent.html 반환
    }
}