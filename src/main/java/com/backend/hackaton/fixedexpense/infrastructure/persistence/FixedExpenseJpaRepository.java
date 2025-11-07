package com.backend.hackaton.fixedexpense.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface FixedExpenseJpaRepository extends JpaRepository<FixedExpenseJpaEntity, Long> {
    List<FixedExpenseJpaEntity> findByUserId(Long userId);
    List<FixedExpenseJpaEntity> findByUserIdAndActive(Long userId, Boolean active);
    Boolean existsByIdAndUserId(Long id, Long userId);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FixedExpenseJpaEntity f WHERE f.userId = :userId AND f.active = :active")
    BigDecimal sumAmountByUserIdAndActive(@Param("userId") Long userId, @Param("active") Boolean active);
}

