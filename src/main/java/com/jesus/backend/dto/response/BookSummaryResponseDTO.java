package com.jesus.backend.dto.response;

import java.util.UUID;

public record BookSummaryResponseDTO (
        UUID id,
        String isbn,
        String title
) {
}
