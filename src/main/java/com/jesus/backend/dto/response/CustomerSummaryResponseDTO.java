package com.jesus.backend.dto.response;

import com.jesus.backend.model.enums.CustomerStatus;

import java.util.UUID;

public record CustomerSummaryResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
