package com.kstn.group4.backend.dto.player;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePitchReviewRequest(
        @NotNull @Min(1) @Max(5) Integer rating,
        @NotBlank String content
) {
}


