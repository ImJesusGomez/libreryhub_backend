package com.jesus.backend.service;

import com.jesus.backend.dto.request.CustomerCreateRequestDTO;
import com.jesus.backend.dto.request.CustomerUpdateRequestDTO;
import com.jesus.backend.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface CustomerService {
    CustomerResponseDTO register(CustomerCreateRequestDTO dto);
    CustomerResponseDTO findById(UUID id);
    Page<CustomerResponseDTO> getCustomers(String firstName, String lastName, int page, int size, String sortBy, String sortDir);
    CustomerResponseDTO update(UUID id, CustomerUpdateRequestDTO dto);
    void uploadCustomerImage(UUID id, MultipartFile image);
}
