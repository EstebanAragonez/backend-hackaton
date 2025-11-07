package com.backend.hackaton.product.application.usecase;

import com.backend.hackaton.product.application.dto.ProductResponse;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListProductsUseCase {

    private final ProductRepository productRepository;

    public ListProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> execute(Long userId, Boolean activeOnly) {
        List<Product> products;

        if (activeOnly != null && activeOnly) {
            products = productRepository.findByUserIdAndActive(userId, true);
        } else {
            products = productRepository.findByUserId(userId);
        }

        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .userId(product.getUserId())
                .name(product.getName())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .salePrice(product.getSalePrice())
                .stock(product.getStock())
                .unit(product.getUnit())
                .image(product.getImage())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .active(product.getActive())
                .build();
    }
}

