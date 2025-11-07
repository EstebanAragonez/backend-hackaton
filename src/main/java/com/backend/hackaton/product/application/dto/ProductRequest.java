package com.backend.hackaton.product.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "El nombre del producto es requerido")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String name;

    private String description;

    @NotNull(message = "El precio de costo es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de costo debe ser mayor a 0")
    private BigDecimal costPrice;

    @NotNull(message = "El precio de venta es requerido")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
    private BigDecimal salePrice;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Size(max = 50, message = "La unidad no puede exceder 50 caracteres")
    private String unit;

    private String image; // Imagen en formato Base64 (opcional)
}

