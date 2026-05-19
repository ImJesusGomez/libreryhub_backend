package com.jesus.backend.dto.response;

public record StatsResponseDTO (
        Long books,
        Long customers,
        Long loans,
        Long loansOverdue
) {
}
