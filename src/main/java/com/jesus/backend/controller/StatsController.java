package com.jesus.backend.controller;

import com.jesus.backend.dto.response.StatsResponseDTO;
import com.jesus.backend.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping()
    public ResponseEntity<StatsResponseDTO> getStats() {
        return new ResponseEntity<>(statsService.getStats(), HttpStatus.OK);
    }
}
