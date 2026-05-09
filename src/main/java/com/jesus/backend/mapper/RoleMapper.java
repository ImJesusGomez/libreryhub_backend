package com.jesus.backend.mapper;

import com.jesus.backend.dto.response.RoleResponseDTO;
import com.jesus.backend.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    // Entity -> Response
    @Mapping(target = "id", ignore = true)
    RoleResponseDTO toRoleResponseDTO(Role role);
}
