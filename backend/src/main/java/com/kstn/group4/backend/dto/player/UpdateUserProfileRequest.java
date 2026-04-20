package com.kstn.group4.backend.dto.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateUserProfileRequest(
        @NotBlank String name,
        @NotBlank
        @Pattern(
                regexp = "^(0|\\+84)(3|5|7|8|9)[0-9]{8}$",
                message = "Số điện thoại không đúng định dạng Việt Nam"
        )
        String phoneNumber,
        String avatarUrl
) {
}


