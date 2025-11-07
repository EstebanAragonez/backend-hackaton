package com.backend.hackaton.product.infrastructure.persistence;

import com.backend.hackaton.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductJpaMapper {

    public Product toDomain(ProductJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Product.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .description(entity.getDescription())
                .costPrice(entity.getCostPrice())
                .salePrice(entity.getSalePrice())
                .stock(entity.getStock())
                .unit(entity.getUnit())
                .image(entity.getImage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .active(entity.getActive())
                .build();
    }

    public ProductJpaEntity toEntity(Product domain) {
        if (domain == null) {
            return null;
        }

        return ProductJpaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .name(domain.getName())
                .description(domain.getDescription())
                .costPrice(domain.getCostPrice())
                .salePrice(domain.getSalePrice())
                .stock(domain.getStock())
                .unit(domain.getUnit())
                .image(domain.getImage())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .active(domain.getActive())
                .build();
    }
}

