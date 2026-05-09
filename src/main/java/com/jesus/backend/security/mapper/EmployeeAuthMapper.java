package com.jesus.backend.security.mapper;

import com.jesus.backend.model.Employee;
import com.jesus.backend.security.dto.RegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoleMapperHelper.class)
public interface EmployeeAuthMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", source = "dto.roles", qualifiedByName = "mapRoleStringsToRoles")
    Employee registerDTOtoEmployee(RegisterRequestDTO dto);
}
