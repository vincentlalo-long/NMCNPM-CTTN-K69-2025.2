package com.kstn.group4.backend.entity.enums;

public enum RoleType {
    SUPER_ADMIN("Super Admin - Full Control"),
    LEAGUE_ADMIN("League Admin - Manage Leagues"),
    TEAM_ADMIN("Team Admin - Manage Team Bookings"),
    PLAYER("Player - Booking Person");

    private final String description;

    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
