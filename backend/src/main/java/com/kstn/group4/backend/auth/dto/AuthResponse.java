package com.kstn.group4.backend.auth.dto;

/**
 * Unified response for Authentication operations (Login, Register, Auth errors)
 * Ensures consistent response format across all auth endpoints
 */
public record AuthResponse(boolean success, String message) {
}