package com.backend.hackaton.fixedexpense.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedExpenseRequest {

    @NotBlank(message = "El nombre del gasto fijo es requerido")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;

    private String description;

    @NotNull(message = "El monto es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    @NotBlank(message = "La frecuencia es requerida")
    @Pattern(regexp = "MONTHLY|WEEKLY|YEARLY", message = "La frecuencia debe ser MONTHLY, WEEKLY o YEARLY")
    private String frequency;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstExecutionDate;
}

