package com.backend.hackaton.fixedexpense.infrastructure.persistence;

import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import org.springframework.stereotype.Component;

@Component
public class FixedExpenseJpaMapper {

    public FixedExpense toDomain(FixedExpenseJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return FixedExpense.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .frequency(entity.getFrequency())
                .nextOccurrenceDate(entity.getNextOccurrenceDate())
                .lastOccurrenceDate(entity.getLastOccurrenceDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .active(entity.getActive())
                .build();
    }

    public FixedExpenseJpaEntity toEntity(FixedExpense domain) {
        if (domain == null) {
            return null;
        }

        return FixedExpenseJpaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .name(domain.getName())
                .description(domain.getDescription())
                .amount(domain.getAmount())
                .frequency(domain.getFrequency())
                .nextOccurrenceDate(domain.getNextOccurrenceDate())
                .lastOccurrenceDate(domain.getLastOccurrenceDate())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .active(domain.getActive())
                .build();
    }
}

