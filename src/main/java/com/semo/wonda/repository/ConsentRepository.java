package com.semo.wonda.repository;

import com.semo.wonda.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    Consent findTopByOrderByCreatedAtDesc();
}