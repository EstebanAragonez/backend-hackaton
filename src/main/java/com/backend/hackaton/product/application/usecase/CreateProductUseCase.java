package com.backend.hackaton.product.application.usecase;

import com.backend.hackaton.product.application.dto.ProductRequest;
import com.backend.hackaton.product.application.dto.ProductResponse;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse execute(Long userId, ProductRequest request) {
        int stock = request.getStock() != null ? request.getStock() : 0;
        // Si se crea con stock 0, se crea inactivo
        boolean active = stock > 0;

        Product product = Product.builder()
                .userId(userId)
                .name(request.getName())
                .description(request.getDescription())
                .costPrice(request.getCostPrice())
                .salePrice(request.getSalePrice())
                .stock(stock)
                .unit(request.getUnit() != null ? request.getUnit() : "unidades")
                .image(request.getImage()) // Guardar imagen en Base64
                .active(active)
                .build();

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
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

