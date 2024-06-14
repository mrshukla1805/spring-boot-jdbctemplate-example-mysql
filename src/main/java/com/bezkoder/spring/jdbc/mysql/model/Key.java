package com.bezkoder.spring.jdbc.mysql.model;

import java.time.LocalDateTime;

public class Key {

    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime keptAliveAt;
    private LocalDateTime blockedAt;
    private boolean isBlocked;

    public Key(String id, LocalDateTime createdAt, LocalDateTime keptAliveAt, LocalDateTime blockedAt, boolean isBlocked) {
        this.id = id;
        this.createdAt = createdAt;
        this.keptAliveAt = keptAliveAt;
        this.blockedAt = blockedAt;
        this.isBlocked = isBlocked;
    }

    public Key() {
    }

    public Key(String id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.keptAliveAt = LocalDateTime.now();
        this.isBlocked = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getKeptAliveAt() {
        return keptAliveAt;
    }

    public void setKeptAliveAt(LocalDateTime keptAliveAt) {
        this.keptAliveAt = keptAliveAt;
    }

    public LocalDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(LocalDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
