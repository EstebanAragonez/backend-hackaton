package com.backend.hackaton.product.application.dto;

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
public class ProductResponse {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private Integer stock;
    private String unit;
    private String image; // Imagen en formato Base64
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;
}

