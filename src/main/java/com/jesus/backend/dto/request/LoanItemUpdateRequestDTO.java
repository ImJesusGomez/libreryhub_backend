package com.jesus.backend.dto.request;


import java.time.LocalDate;
import java.util.UUID;

public record LoanItemUpdateRequestDTO (
        LocalDate realReturnDate,
        Boolean damaged
) {
}
