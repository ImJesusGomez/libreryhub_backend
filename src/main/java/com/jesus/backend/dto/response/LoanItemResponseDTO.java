package com.jesus.backend.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record LoanItemResponseDTO (
        UUID id,
        LocalDate estimatedReturnDate,
        LocalDate realReturnDate,
        Boolean damaged,
        Integer quantity,
        BookSummaryResponseDTO book
) {
}
