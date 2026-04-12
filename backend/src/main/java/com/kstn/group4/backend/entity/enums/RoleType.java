package com.kstn.group4.backend.entity.enums;

public enum RoleType {
    ADMIN("Admin - Full Control"),
    PLAYER("Player - Booking Person");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
