package com.backend.hackaton.product.application.usecase;

import com.backend.hackaton.product.application.dto.ProductResponse;
import com.backend.hackaton.product.application.exception.ProductNotFoundException;
import com.backend.hackaton.product.application.exception.ProductNotOwnedException;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetProductUseCase {

    private final ProductRepository productRepository;

    public GetProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponse execute(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        if (!product.getUserId().equals(userId)) {
            throw new ProductNotOwnedException("No tienes permiso para ver este producto");
        }

        return mapToResponse(product);
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

