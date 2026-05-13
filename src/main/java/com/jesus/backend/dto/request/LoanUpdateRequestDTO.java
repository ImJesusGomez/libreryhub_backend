package com.jesus.backend.dto.request;

import com.jesus.backend.model.enums.LoanStatus;

import java.util.UUID;

public record LoanUpdateRequestDTO (
        LoanStatus status
) {
}
