package com.backend.hackaton.fixedexpense.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FixedExpenseJpaRepository extends JpaRepository<FixedExpenseJpaEntity, Long> {
    List<FixedExpenseJpaEntity> findByUserId(Long userId);
    List<FixedExpenseJpaEntity> findByUserIdAndActive(Long userId, Boolean active);
    Optional<FixedExpenseJpaEntity> findByIdAndUserId(Long id, Long userId);
    Boolean existsByIdAndUserId(Long id, Long userId);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FixedExpenseJpaEntity f WHERE f.userId = :userId AND f.active = :active")
    BigDecimal sumAmountByUserIdAndActive(@Param("userId") Long userId, @Param("active") Boolean active);

    @Query("SELECT f FROM FixedExpenseJpaEntity f WHERE f.active = true AND f.nextOccurrenceDate <= :dueDate")
    List<FixedExpenseJpaEntity> findActiveDueUntil(@Param("dueDate") LocalDate dueDate);
}

