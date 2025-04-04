package com.semo.wonda.controller;

import com.semo.wonda.entity.Consent;
import com.semo.wonda.service.ConsentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consents")
public class ConsentController {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @PostMapping
    public Consent saveConsent(@RequestBody Consent consent) {
        return consentService.saveConsent(consent.getContent());
    }

    @GetMapping
    public Consent getLastConsents() {
        return consentService.getLastConsents();
    }

    @GetMapping("/all")
    public List<Consent> getAllConsents() {
        return consentService.getAllConsents();
    }


}
