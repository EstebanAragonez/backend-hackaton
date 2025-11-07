package com.backend.hackaton.fixedexpense.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedExpenseResponse {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private BigDecimal amount;
    private String frequency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}

