package com.backend.hackaton.product.application.usecase;

import com.backend.hackaton.product.application.dto.ProductResponse;
import com.backend.hackaton.product.application.exception.ProductNotFoundException;
import com.backend.hackaton.product.application.exception.ProductNotOwnedException;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ToggleProductStatusUseCase {

    private final ProductRepository productRepository;

    public ToggleProductStatusUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse execute(Long userId, Long productId, Boolean active) {
        // Buscar el producto y verificar que pertenece al usuario
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        if (!product.getUserId().equals(userId)) {
            throw new ProductNotOwnedException("No tienes permiso para cambiar el estado de este producto");
        }

        // Actualizar el estado activo
        product.setActive(active);

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
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

