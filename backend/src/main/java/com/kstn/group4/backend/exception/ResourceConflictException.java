package com.kstn.group4.backend.exception;

// Đây là Exception Generic - Dùng cho mọi module khi có xung đột dữ liệu
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}