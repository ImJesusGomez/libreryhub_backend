package com.jesus.backend.mapper;

import com.jesus.backend.dto.response.LoanItemResponseDTO;
import com.jesus.backend.model.LoanItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanItemMapper {
    LoanItemResponseDTO toLoanItemResponseDTO(LoanItem loanItem);
}
