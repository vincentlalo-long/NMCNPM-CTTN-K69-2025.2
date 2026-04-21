package com.kstn.group4.backend.dto.manager;

import java.math.BigDecimal;

public record RevenueResponse(
        Integer ownerId,
        BigDecimal totalRevenue,
        Integer completedBookings
) {
}


