package com.kstn.group4.backend.venue.dto.admin;

import java.math.BigDecimal;

public record AdminVenueResponseDTO(
        Integer id,
        String name,
        String address,
        String imageUrl,
        BigDecimal revenue,
        Long totalPitches
) {
}
