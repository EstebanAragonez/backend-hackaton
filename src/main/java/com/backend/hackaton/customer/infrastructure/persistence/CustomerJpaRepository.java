package com.backend.hackaton.customer.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {
    List<CustomerJpaEntity> findByUserId(Long userId);
    Optional<CustomerJpaEntity> findByUserIdAndContactNumber(Long userId, String contactNumber);
    Boolean existsByIdAndUserId(Long id, Long userId);
}

