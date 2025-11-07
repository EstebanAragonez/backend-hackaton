package com.backend.hackaton.fixedexpense.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFixedExpenseRequest {

    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    @Pattern(regexp = "MONTHLY|WEEKLY|YEARLY", message = "La frecuencia debe ser MONTHLY, WEEKLY o YEARLY")
    private String frequency;
}

