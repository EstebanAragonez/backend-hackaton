package com.backend.hackaton.financial.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FinancialTransactionRepository {
    FinancialTransaction save(FinancialTransaction transaction);
    Optional<FinancialTransaction> findById(Long id);
    List<FinancialTransaction> findByUserId(Long userId);
    List<FinancialTransaction> findByUserIdAndType(Long userId, String type);
    List<FinancialTransaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    void delete(Long id);
}

