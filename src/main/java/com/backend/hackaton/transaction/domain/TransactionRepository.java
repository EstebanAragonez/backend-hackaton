package com.backend.hackaton.transaction.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(Long id);
    List<Transaction> findByUserId(Long userId);
    List<Transaction> findByUserIdAndType(Long userId, TransactionType type);
    List<Transaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    List<Transaction> findByUserIdAndProductId(Long userId, Long productId);
    BigDecimal sumAmountByUserIdAndType(Long userId, TransactionType type);
    BigDecimal sumAmountByUserIdAndTypeAndDateBetween(Long userId, TransactionType type, LocalDateTime start, LocalDateTime end);
}

