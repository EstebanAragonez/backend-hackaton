package com.backend.hackaton.product.application.service;

import com.backend.hackaton.product.application.exception.InsufficientStockException;
import com.backend.hackaton.product.application.exception.ProductNotFoundException;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductStockService {

    private final ProductRepository productRepository;

    public ProductStockService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Actualiza el stock de un producto y lo desactiva automáticamente si el stock llega a 0
     * @param productId ID del producto
     * @param quantity Cantidad a restar del stock (siempre positivo, se resta del stock actual)
     * @return Producto actualizado
     */
    @Transactional
    public Product updateStockAndCheckAvailability(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        // Verificar stock disponible
        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Stock insuficiente. Stock disponible: " + product.getStock());
        }

        // Actualizar stock (restar cantidad vendida)
        int newStock = product.getStock() - quantity;
        product.setStock(newStock);

        // Si el stock llega a 0, desactivar el producto automáticamente
        if (newStock == 0) {
            product.setActive(false);
        }

        return productRepository.save(product);
    }

    /**
     * Verifica y desactiva productos que tengan stock en 0
     * @param productId ID del producto a verificar
     */
    @Transactional
    public void deactivateIfOutOfStock(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        if (product.getStock() == 0 && product.getActive()) {
            product.setActive(false);
            productRepository.save(product);
        }
    }
}

