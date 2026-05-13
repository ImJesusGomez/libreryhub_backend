package com.jesus.backend.mapper;

import com.jesus.backend.dto.response.LoanResponseDTO;
import com.jesus.backend.model.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanResponseDTO toLoanResponseDTO(Loan loan);
}
