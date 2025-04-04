package com.semo.wonda.service;

import com.semo.wonda.entity.Consent;
import com.semo.wonda.repository.ConsentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsentService {

    private final ConsentRepository consentRepository;

    public ConsentService(ConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    public Consent saveConsent(String content) {
        return consentRepository.save(new Consent(content));
    }

    public List<Consent> getAllConsents() {
        return consentRepository.findAll();
    }

    public Consent getLastConsents(){
        return consentRepository.findTopByOrderByCreatedAtDesc();
    }
}