package com.backend.hackaton.fixedexpense.application.service;

import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseFrequency;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseRepository;
import com.backend.hackaton.transaction.domain.Transaction;
import com.backend.hackaton.transaction.domain.TransactionRepository;
import com.backend.hackaton.transaction.domain.TransactionType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FixedExpenseRenewalJob {

    private final FixedExpenseRepository fixedExpenseRepository;
    private final TransactionRepository transactionRepository;

    public FixedExpenseRenewalJob(FixedExpenseRepository fixedExpenseRepository,
                                  TransactionRepository transactionRepository) {
        this.fixedExpenseRepository = fixedExpenseRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * *", zone = "UTC")
    public void renewDueExpenses() {
        LocalDate today = LocalDate.now();
        List<FixedExpense> dueExpenses = fixedExpenseRepository.findActiveDueUntil(today);

        for (FixedExpense expense : dueExpenses) {
            LocalDate executionDate = expense.getNextOccurrenceDate();
            if (executionDate == null) {
                executionDate = today;
            }

            while (!executionDate.isAfter(today) && Boolean.TRUE.equals(expense.getActive())) {
                createExpenseTransaction(expense, executionDate);
                expense.setLastOccurrenceDate(executionDate);
                executionDate = calculateNextOccurrence(expense.getFrequency(), executionDate);
            }

            expense.setNextOccurrenceDate(executionDate);
            fixedExpenseRepository.save(expense);
        }
    }

    private void createExpenseTransaction(FixedExpense expense, LocalDate executionDate) {
        transactionRepository.save(Transaction.builder()
                .userId(expense.getUserId())
                .type(TransactionType.EXPENSE)
                .description(expense.getName())
                .amount(expense.getAmount() != null ? expense.getAmount() : BigDecimal.ZERO)
                .transactionDate(executionDate.atStartOfDay())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    private LocalDate calculateNextOccurrence(FixedExpenseFrequency frequency, LocalDate from) {
        return switch (frequency) {
            case WEEKLY -> from.plusWeeks(1);
            case MONTHLY -> from.plusMonths(1);
            case YEARLY -> from.plusYears(1);
        };
    }
}


