package com.backend.hackaton.product.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
    List<ProductJpaEntity> findByUserId(Long userId);
    List<ProductJpaEntity> findByUserIdAndActive(Long userId, Boolean active);
    Boolean existsByIdAndUserId(Long id, Long userId);
}

