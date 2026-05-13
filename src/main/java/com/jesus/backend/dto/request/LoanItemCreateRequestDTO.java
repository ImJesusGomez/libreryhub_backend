package com.jesus.backend.dto.request;


import java.time.LocalDate;
import java.util.UUID;

public record LoanItemCreateRequestDTO (
        LocalDate estimatedReturnDate,
        Integer quantity,
        UUID bookId
) {
}
