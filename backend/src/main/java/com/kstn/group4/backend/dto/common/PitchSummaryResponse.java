package com.kstn.group4.backend.dto.common;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record PitchSummaryResponse(
        Integer id,
        String name,
        String address,
        String pitchType,
        String surfaceType,
        BigDecimal basePrice,
        String imageUrl,
        String status
) {
}


