package com.jesus.backend.mapper;

import com.jesus.backend.dto.request.CustomerCreateRequestDTO;
import com.jesus.backend.dto.response.CustomerResponseDTO;
import com.jesus.backend.dto.response.CustomerSummaryResponseDTO;
import com.jesus.backend.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Entity -> DTO
    CustomerResponseDTO toCustomerResponseDTO(Customer customer);
    CustomerSummaryResponseDTO toCustomerSummaryResponseDTO(Customer customer);

    // DTO -> Entity
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "normName")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "normName")
    @Mapping(target = "email", source = "email", qualifiedByName = "normEmail")
    @Mapping(target = "phoneNumber", source = "phoneNumber", qualifiedByName = "normPhone")
    Customer toEntity(CustomerCreateRequestDTO dto);

    // Funciones de limpieza
    @Named("normName")
    default String normalizeName(String name) {
        return name == null ? null : name.trim();
    }

    @Named("normEmail")
    default String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    @Named("normPhone")
    default String normalizePhoneNumber(String phoneNumber) {
        return phoneNumber == null
                ? null
                : phoneNumber.replaceAll("[^0-9]", "");
    }
}
