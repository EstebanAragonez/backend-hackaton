package com.backend.hackaton.product.application.usecase;

import com.backend.hackaton.product.application.dto.ProductRequest;
import com.backend.hackaton.product.application.dto.ProductResponse;
import com.backend.hackaton.product.application.exception.ProductNotFoundException;
import com.backend.hackaton.product.application.exception.ProductNotOwnedException;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse execute(Long userId, Long productId, ProductRequest request) {
        // Buscar el producto y verificar que pertenece al usuario
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        if (!product.getUserId().equals(userId)) {
            throw new ProductNotOwnedException("No tienes permiso para editar este producto");
        }

        // Actualizar solo los campos que se enviaron
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getCostPrice() != null) {
            product.setCostPrice(request.getCostPrice());
        }
        if (request.getSalePrice() != null) {
            product.setSalePrice(request.getSalePrice());
        }
        if (request.getStock() != null) {
            product.setStock(request.getStock());
            // Si el stock llega a 0, desactivar el producto automáticamente
            // Si el stock es mayor a 0 y estaba inactivo por falta de stock, se puede reactivar manualmente
            if (request.getStock() == 0) {
                product.setActive(false);
            }
        }
        if (request.getUnit() != null) {
            product.setUnit(request.getUnit());
        }
        if (request.getImage() != null) {
            product.setImage(request.getImage()); // Actualizar imagen
        }

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

