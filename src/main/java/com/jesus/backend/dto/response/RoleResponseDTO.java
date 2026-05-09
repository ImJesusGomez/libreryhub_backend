package com.jesus.backend.dto.response;

import java.util.UUID;

public record RoleResponseDTO(
        UUID id,
        String name
) {
}
