package com.jesus.backend.dto.request;

import java.util.UUID;

public record LoanCreateRequestDTO (
        UUID customerId,
        LoanItemCreateRequestDTO[] items
) {
}
