package com.jesus.backend.dto.response;

import java.util.Set;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Set<RoleResponseDTO> roles
) {
}
