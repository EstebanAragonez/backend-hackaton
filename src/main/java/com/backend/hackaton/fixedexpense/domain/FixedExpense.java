package com.backend.hackaton.fixedexpense.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedExpense {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private BigDecimal amount; // Monto del gasto fijo
    private FixedExpenseFrequency frequency; // WEEKLY, MONTHLY, YEARLY
    private LocalDate nextOccurrenceDate;
    private LocalDate lastOccurrenceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}

