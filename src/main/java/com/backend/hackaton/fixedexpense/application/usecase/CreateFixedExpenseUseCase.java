package com.backend.hackaton.fixedexpense.application.usecase;

import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseRequest;
import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseResponse;
import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateFixedExpenseUseCase {

    private final FixedExpenseRepository fixedExpenseRepository;

    public CreateFixedExpenseUseCase(FixedExpenseRepository fixedExpenseRepository) {
        this.fixedExpenseRepository = fixedExpenseRepository;
    }

    @Transactional
    public FixedExpenseResponse execute(Long userId, FixedExpenseRequest request) {
        FixedExpense fixedExpense = FixedExpense.builder()
                .userId(userId)
                .name(request.getName())
                .description(request.getDescription())
                .amount(request.getAmount())
                .frequency(request.getFrequency())
                .active(true)
                .build();

        FixedExpense savedExpense = fixedExpenseRepository.save(fixedExpense);

        return mapToResponse(savedExpense);
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

