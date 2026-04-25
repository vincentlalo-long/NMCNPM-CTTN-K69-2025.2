package com.kstn.group4.backend.venue.dto.admin;

import com.kstn.group4.backend.venue.entity.PitchType;
import java.math.BigDecimal;
import java.util.List;

public record PitchDetailResponse(
        Integer id,
        String name,
        PitchType pitchType,
        Boolean isActive,
        Integer venueId,
        String venueName,
        BigDecimal basePrice,
        List<SlotPriceResponse> slotPrices
) {
    public record SlotPriceResponse(
            Integer slotNumber,
            BigDecimal weekdayPrice,
            BigDecimal weekendPrice
    ) {
    }
}
