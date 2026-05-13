package com.jesus.backend.service;

import com.jesus.backend.dto.request.LoanCreateRequestDTO;
import com.jesus.backend.dto.request.LoanItemUpdateRequestDTO;
import com.jesus.backend.dto.request.LoanUpdateRequestDTO;
import com.jesus.backend.dto.response.LoanItemResponseDTO;
import com.jesus.backend.dto.response.LoanResponseDTO;
import com.jesus.backend.model.enums.LoanStatus;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface LoanService {
    LoanResponseDTO create(LoanCreateRequestDTO dto);
    LoanResponseDTO findById(UUID id);
    LoanItemResponseDTO findLoanItemById(UUID id);
    Page<LoanResponseDTO> getLoans(LoanStatus status, String customerFirstName, int page, int size, String sortBy, String sortDir);
    LoanItemResponseDTO returnLoanItem(UUID id, LoanItemUpdateRequestDTO dto);
}
