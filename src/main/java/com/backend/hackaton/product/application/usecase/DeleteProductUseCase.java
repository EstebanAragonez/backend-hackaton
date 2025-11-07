package com.backend.hackaton.product.application.usecase;

import com.backend.hackaton.product.application.exception.ProductNotFoundException;
import com.backend.hackaton.product.application.exception.ProductNotOwnedException;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteProductUseCase {

    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void execute(Long userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        if (!product.getUserId().equals(userId)) {
            throw new ProductNotOwnedException("No tienes permiso para eliminar este producto");
        }

        productRepository.deleteById(productId);
    }
}

