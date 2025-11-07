package com.backend.hackaton.fixedexpense.application.usecase;

import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseResponse;
import com.backend.hackaton.fixedexpense.application.exception.FixedExpenseNotFoundException;
import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ToggleFixedExpenseStatusUseCase {

    private final FixedExpenseRepository fixedExpenseRepository;

    public ToggleFixedExpenseStatusUseCase(FixedExpenseRepository fixedExpenseRepository) {
        this.fixedExpenseRepository = fixedExpenseRepository;
    }

    @Transactional
    public FixedExpenseResponse execute(Long userId, Long expenseId, Boolean active) {
        // Buscar el gasto fijo y verificar que pertenece al usuario
        FixedExpense fixedExpense = fixedExpenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new FixedExpenseNotFoundException("Gasto fijo no encontrado"));

        // Actualizar el estado activo
        fixedExpense.setActive(active);
        if (Boolean.TRUE.equals(active)
                && fixedExpense.getNextOccurrenceDate() != null
                && fixedExpense.getNextOccurrenceDate().isBefore(LocalDate.now())) {
            fixedExpense.setNextOccurrenceDate(LocalDate.now());
        }

        FixedExpense updatedExpense = fixedExpenseRepository.save(fixedExpense);

        return mapToResponse(updatedExpense);
    }

    private FixedExpenseResponse mapToResponse(FixedExpense fixedExpense) {
        return FixedExpenseResponse.builder()
                .id(fixedExpense.getId())
                .userId(fixedExpense.getUserId())
                .name(fixedExpense.getName())
                .description(fixedExpense.getDescription())
                .amount(fixedExpense.getAmount())
                .frequency(fixedExpense.getFrequency().name())
                .nextOccurrenceDate(fixedExpense.getNextOccurrenceDate())
                .lastOccurrenceDate(fixedExpense.getLastOccurrenceDate())
                .createdAt(fixedExpense.getCreatedAt())
                .updatedAt(fixedExpense.getUpdatedAt())
                .active(fixedExpense.getActive())
                .build();
    }
}

