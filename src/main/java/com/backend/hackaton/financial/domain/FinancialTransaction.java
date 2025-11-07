package com.backend.hackaton.financial.domain;

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
public class FinancialTransaction {
    private Long id;
    private Long userId; // Usuario propietario de la transacción
    private String type; // INCOME o EXPENSE
    private String category; // Categoría de la transacción
    private String description;
    private BigDecimal amount; // Monto de la transacción
    private LocalDateTime transactionDate;
    private String paymentMethod; // Efectivo, Transferencia, Tarjeta, etc.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}

