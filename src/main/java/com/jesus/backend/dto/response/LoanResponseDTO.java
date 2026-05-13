package com.jesus.backend.dto.response;

import com.jesus.backend.model.enums.LoanStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoanResponseDTO (
        UUID id,
        LocalDateTime loanDate,
        LoanStatus status,
        CustomerSummaryResponseDTO customer,
        LoanItemResponseDTO[] items
) {
}
