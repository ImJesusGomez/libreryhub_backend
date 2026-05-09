package com.jesus.backend.security.mapper;

import com.jesus.backend.model.Role;
import com.jesus.backend.repository.RoleRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapperHelper {
    private final RoleRepository roleRepository;

    public RoleMapperHelper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Named("mapRoleStringsToRoles")
    public Set<Role> mapRoleStringsToRoles(Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty()) {
            return roleRepository
                    .findByName("ROLE_ADMIN")
                    .map(Collections::singleton)
                    .orElseThrow(() -> new RuntimeException(
                            "Error: 'ROLE_ADMIN' not found."
                    ));
        }

        return roleNames.stream()
                .map(
                        roleName -> roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found: " + roleName))
                ).collect(Collectors.toSet());
    }

}
