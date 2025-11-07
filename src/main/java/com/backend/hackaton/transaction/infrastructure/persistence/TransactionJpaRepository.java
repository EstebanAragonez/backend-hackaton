package com.backend.hackaton.transaction.infrastructure.persistence;

import com.backend.hackaton.transaction.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {
    List<TransactionJpaEntity> findByUserId(Long userId);
    List<TransactionJpaEntity> findByUserIdAndType(Long userId, TransactionType type);
    List<TransactionJpaEntity> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<TransactionJpaEntity> findByUserIdAndProductId(Long userId, Long productId);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionJpaEntity t WHERE t.userId = :userId AND t.type = :type")
    BigDecimal sumAmountByUserIdAndType(@Param("userId") Long userId, @Param("type") TransactionType type);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionJpaEntity t WHERE t.userId = :userId AND t.type = :type AND t.transactionDate BETWEEN :start AND :end")
    BigDecimal sumAmountByUserIdAndTypeAndDateBetween(@Param("userId") Long userId, @Param("type") TransactionType type, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}

