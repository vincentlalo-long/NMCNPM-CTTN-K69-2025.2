package com.kstn.group4.backend.dto.player;

import java.math.BigDecimal;

public record PaymentLinkResponse(
        Integer bookingId,
        BigDecimal amount,
        String paymentUrl
) {
}


