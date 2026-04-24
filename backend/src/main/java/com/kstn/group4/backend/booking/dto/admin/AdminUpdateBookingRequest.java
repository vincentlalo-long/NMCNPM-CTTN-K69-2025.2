package com.kstn.group4.backend.booking.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AdminUpdateBookingRequest(
        @NotBlank(message = "Trạng thái không được để trống")
        @Pattern(regexp = "^(PENDING|BOOKED|PLAYING|COMPLETED|CANCELLED)$", 
                 message = "Trạng thái không hợp lệ. Chỉ chấp nhận PENDING, BOOKED, PLAYING, COMPLETED, CANCELLED")
        String status,

        String adminNote // Lưu lý do hủy đơn hoặc ghi chú thêm
) {
}