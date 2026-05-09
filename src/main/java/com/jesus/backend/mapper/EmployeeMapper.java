package com.jesus.backend.mapper;

import com.jesus.backend.dto.response.EmployeeResponseDTO;
import com.jesus.backend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    // Entity -> Response
    EmployeeResponseDTO toEmployeeResponseDTO(Employee employee);
}
