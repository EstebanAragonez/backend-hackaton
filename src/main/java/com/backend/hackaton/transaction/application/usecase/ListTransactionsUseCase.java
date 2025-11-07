package com.backend.hackaton.transaction.application.usecase;

import com.backend.hackaton.transaction.application.dto.TransactionResponse;
import com.backend.hackaton.transaction.domain.Transaction;
import com.backend.hackaton.transaction.domain.TransactionRepository;
import com.backend.hackaton.transaction.domain.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public ListTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> execute(Long userId, TransactionType type, LocalDateTime startDate, LocalDateTime endDate) {
        List<Transaction> transactions;

        if (type != null && startDate != null && endDate != null) {
            transactions = transactionRepository.findByUserIdAndTransactionDateBetween(userId, startDate, endDate)
                    .stream()
                    .filter(t -> t.getType() == type)
                    .collect(Collectors.toList());
        } else if (type != null) {
            transactions = transactionRepository.findByUserIdAndType(userId, type);
        } else if (startDate != null && endDate != null) {
            transactions = transactionRepository.findByUserIdAndTransactionDateBetween(userId, startDate, endDate);
        } else {
            transactions = transactionRepository.findByUserId(userId);
        }

        return transactions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .userId(transaction.getUserId())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .productId(transaction.getProductId())
                .quantity(transaction.getQuantity())
                .customerId(transaction.getCustomerId())
                .transactionDate(transaction.getTransactionDate())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .build();
    }
}

