package com.jesus.backend.dto.response;

import com.jesus.backend.model.enums.CustomerStatus;

import java.util.UUID;

public record CustomerResponseDTO (
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        CustomerStatus status
) {
}
