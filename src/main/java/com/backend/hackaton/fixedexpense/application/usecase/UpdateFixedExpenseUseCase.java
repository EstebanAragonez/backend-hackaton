package com.backend.hackaton.fixedexpense.application.usecase;

import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseResponse;
import com.backend.hackaton.fixedexpense.application.dto.UpdateFixedExpenseRequest;
import com.backend.hackaton.fixedexpense.application.exception.FixedExpenseNotFoundException;
import com.backend.hackaton.fixedexpense.application.exception.FixedExpenseNotOwnedException;
import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateFixedExpenseUseCase {

    private final FixedExpenseRepository fixedExpenseRepository;

    public UpdateFixedExpenseUseCase(FixedExpenseRepository fixedExpenseRepository) {
        this.fixedExpenseRepository = fixedExpenseRepository;
    }

    @Transactional
    public FixedExpenseResponse execute(Long userId, Long expenseId, UpdateFixedExpenseRequest request) {
        // Buscar el gasto fijo y verificar que pertenece al usuario
        FixedExpense fixedExpense = fixedExpenseRepository.findById(expenseId)
                .orElseThrow(() -> new FixedExpenseNotFoundException("Gasto fijo no encontrado"));

        if (!fixedExpense.getUserId().equals(userId)) {
            throw new FixedExpenseNotOwnedException("No tienes permiso para editar este gasto fijo");
        }

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
            fixedExpense.setFrequency(request.getFrequency());
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
                .frequency(fixedExpense.getFrequency())
                .createdAt(fixedExpense.getCreatedAt())
                .updatedAt(fixedExpense.getUpdatedAt())
                .active(fixedExpense.getActive())
                .build();
    }
}

