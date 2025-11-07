package com.backend.hackaton.fixedexpense.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FixedExpenseRepository {
    FixedExpense save(FixedExpense fixedExpense);
    Optional<FixedExpense> findById(Long id);
    List<FixedExpense> findByUserId(Long userId);
    List<FixedExpense> findByUserIdAndActive(Long userId, Boolean active);
    void deleteById(Long id);
    Boolean existsByIdAndUserId(Long id, Long userId);
    BigDecimal sumAmountByUserIdAndActive(Long userId, Boolean active);
}

