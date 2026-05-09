package com.jesus.backend.security.dto;

import com.jesus.backend.dto.response.EmployeeResponseDTO;

public record JwtAuthResponseDTO(String accessToken, EmployeeResponseDTO employee, String tokenType) {
    public JwtAuthResponseDTO(String accessToken, EmployeeResponseDTO employee) {
        this(accessToken, employee, "Bearer ");
    }
}
