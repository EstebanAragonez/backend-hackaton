package com.backend.hackaton.transaction.infrastructure.persistence;

import com.backend.hackaton.transaction.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionJpaMapper {

    public Transaction toDomain(TransactionJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Transaction.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .type(entity.getType())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .customerId(entity.getCustomerId())
                .transactionDate(entity.getTransactionDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public TransactionJpaEntity toEntity(Transaction domain) {
        if (domain == null) {
            return null;
        }

        return TransactionJpaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .type(domain.getType())
                .description(domain.getDescription())
                .amount(domain.getAmount())
                .productId(domain.getProductId())
                .quantity(domain.getQuantity())
                .customerId(domain.getCustomerId())
                .transactionDate(domain.getTransactionDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}

