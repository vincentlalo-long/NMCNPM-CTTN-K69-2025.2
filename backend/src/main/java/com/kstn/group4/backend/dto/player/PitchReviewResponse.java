package com.kstn.group4.backend.dto.player;

import java.time.LocalDateTime;

public record PitchReviewResponse(
        Integer id,
        Integer pitchId,
        Integer playerId,
        String playerName,
        Integer rating,
        String content,
        LocalDateTime createdAt
) {
}


