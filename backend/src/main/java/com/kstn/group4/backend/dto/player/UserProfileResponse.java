package com.kstn.group4.backend.dto.player;

public record UserProfileResponse(
        Integer id,
        String name,
        String email,
        String phoneNumber,
        String avatarUrl,
        String role
) {
}


