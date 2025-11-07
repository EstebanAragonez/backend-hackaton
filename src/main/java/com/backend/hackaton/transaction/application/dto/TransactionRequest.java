package com.backend.hackaton.transaction.application.dto;

import com.backend.hackaton.transaction.domain.TransactionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    @NotNull(message = "El tipo de transacción es requerido")
    private TransactionType type;

    @NotBlank(message = "La descripción es requerida")
    private String description;

    @NotNull(message = "El monto es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    private Long productId; // Para ventas

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer quantity; // Para ventas

    @Valid
    private CustomerData customer; // Datos del cliente para ventas (opcional)

    private Long customerId; // ID del cliente existente (opcional, alternativa a customer)

    private LocalDateTime transactionDate;
}

