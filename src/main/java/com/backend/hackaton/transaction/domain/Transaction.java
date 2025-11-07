package com.backend.hackaton.transaction.domain;

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
public class Transaction {
    private Long id;
    private Long userId;
    private TransactionType type; // INCOME o EXPENSE
    private String description;
    private BigDecimal amount;
    private Long productId; // Para ventas (opcional)
    private Integer quantity; // Cantidad vendida (para ventas)
    private Long customerId; // Cliente asociado a la venta (opcional)
    private LocalDateTime transactionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

