package com.backend.hackaton.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findByUserId(Long userId);
    List<Product> findByUserIdAndActive(Long userId, Boolean active);
    void deleteById(Long id);
    Boolean existsByIdAndUserId(Long id, Long userId);
}

