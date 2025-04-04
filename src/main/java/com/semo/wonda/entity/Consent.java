package com.semo.wonda.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 기본 생성자
    public Consent() {}

    public Consent(String content) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }

    // Getter & Setter
    public Long getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setContent(String content) { this.content = content; }
}