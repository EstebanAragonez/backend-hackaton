package com.backend.hackaton.fixedexpense.application.usecase;

import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseResponse;
import com.backend.hackaton.fixedexpense.application.dto.UpdateFixedExpenseRequest;
import com.backend.hackaton.fixedexpense.application.exception.FixedExpenseNotFoundException;
import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseFrequency;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UpdateFixedExpenseUseCase {

    private final FixedExpenseRepository fixedExpenseRepository;

    public UpdateFixedExpenseUseCase(FixedExpenseRepository fixedExpenseRepository) {
        this.fixedExpenseRepository = fixedExpenseRepository;
    }

    @Transactional
    public FixedExpenseResponse execute(Long userId, Long expenseId, UpdateFixedExpenseRequest request) {
        // Buscar el gasto fijo y verificar que pertenece al usuario
        FixedExpense fixedExpense = fixedExpenseRepository.findByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new FixedExpenseNotFoundException("Gasto fijo no encontrado"));

        // Actualizar solo los campos que se enviaron
        if (request.getName() != null) {
            fixedExpense.setName(request.getName());
        }
        if (request.getDescription() != null) {
            fixedExpense.setDescription(request.getDescription());
        }
        if (request.getAmount() != null) {
            fixedExpense.setAmount(request.getAmount());
        }
        if (request.getFrequency() != null) {
            FixedExpenseFrequency frequency = FixedExpenseFrequency.valueOf(request.getFrequency());
            fixedExpense.setFrequency(frequency);
            fixedExpense.setNextOccurrenceDate(realignNextOccurrence(fixedExpense, frequency));
        }
        if (request.getNextOccurrenceDate() != null) {
            fixedExpense.setNextOccurrenceDate(request.getNextOccurrenceDate());
        }
        if (request.getActive() != null) {
            fixedExpense.setActive(request.getActive());
            if (Boolean.TRUE.equals(request.getActive())
                    && fixedExpense.getNextOccurrenceDate() != null
                    && fixedExpense.getNextOccurrenceDate().isBefore(LocalDate.now())) {
                fixedExpense.setNextOccurrenceDate(LocalDate.now());
            }
        }

        if (fixedExpense.getNextOccurrenceDate() == null) {
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

    private LocalDate realignNextOccurrence(FixedExpense fixedExpense, FixedExpenseFrequency frequency) {
        LocalDate base = fixedExpense.getLastOccurrenceDate() != null
                ? fixedExpense.getLastOccurrenceDate()
                : fixedExpense.getNextOccurrenceDate();

        if (base == null) {
            return LocalDate.now();
        }

        LocalDate next = fixedExpense.getLastOccurrenceDate() != null
                ? calculateNextOccurrence(frequency, base)
                : base;

        while (next.isBefore(LocalDate.now())) {
            next = calculateNextOccurrence(frequency, next);
        }
        return next;
    }

    private LocalDate calculateNextOccurrence(FixedExpenseFrequency frequency, LocalDate from) {
        return switch (frequency) {
            case WEEKLY -> from.plusWeeks(1);
            case MONTHLY -> from.plusMonths(1);
            case YEARLY -> from.plusYears(1);
        };
    }
}

