package com.kstn.group4.backend.dto.manager;

import jakarta.validation.constraints.NotBlank;

public record UpdateBookingStatusRequest(
        @NotBlank String status
) {
}


