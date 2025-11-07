package com.backend.hackaton.fixedexpense.presentation;

import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseRequest;
import com.backend.hackaton.fixedexpense.application.dto.FixedExpenseResponse;
import com.backend.hackaton.fixedexpense.application.dto.UpdateFixedExpenseRequest;
import com.backend.hackaton.fixedexpense.application.usecase.CreateFixedExpenseUseCase;
import com.backend.hackaton.fixedexpense.application.usecase.ListFixedExpensesUseCase;
import com.backend.hackaton.fixedexpense.application.usecase.ToggleFixedExpenseStatusUseCase;
import com.backend.hackaton.fixedexpense.application.usecase.UpdateFixedExpenseUseCase;
import com.backend.hackaton.shared.security.JwtUserExtractor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fixed-expenses")
public class FixedExpenseController {

    private final CreateFixedExpenseUseCase createFixedExpenseUseCase;
    private final ListFixedExpensesUseCase listFixedExpensesUseCase;
    private final UpdateFixedExpenseUseCase updateFixedExpenseUseCase;
    private final ToggleFixedExpenseStatusUseCase toggleFixedExpenseStatusUseCase;
    private final JwtUserExtractor jwtUserExtractor;

    public FixedExpenseController(
            CreateFixedExpenseUseCase createFixedExpenseUseCase,
            ListFixedExpensesUseCase listFixedExpensesUseCase,
            UpdateFixedExpenseUseCase updateFixedExpenseUseCase,
            ToggleFixedExpenseStatusUseCase toggleFixedExpenseStatusUseCase,
            JwtUserExtractor jwtUserExtractor) {
        this.createFixedExpenseUseCase = createFixedExpenseUseCase;
        this.listFixedExpensesUseCase = listFixedExpensesUseCase;
        this.updateFixedExpenseUseCase = updateFixedExpenseUseCase;
        this.toggleFixedExpenseStatusUseCase = toggleFixedExpenseStatusUseCase;
        this.jwtUserExtractor = jwtUserExtractor;
    }

    @PostMapping
    public ResponseEntity<FixedExpenseResponse> createFixedExpense(@Valid @RequestBody FixedExpenseRequest request) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        FixedExpenseResponse response = createFixedExpenseUseCase.execute(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FixedExpenseResponse>> getFixedExpenses(
            @RequestParam(required = false) Boolean activeOnly) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        List<FixedExpenseResponse> expenses = listFixedExpensesUseCase.execute(userId, activeOnly);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FixedExpenseResponse> updateFixedExpense(
            @PathVariable Long id,
            @Valid @RequestBody UpdateFixedExpenseRequest request) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        FixedExpenseResponse response = updateFixedExpenseUseCase.execute(userId, id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FixedExpenseResponse> toggleFixedExpenseStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        FixedExpenseResponse response = toggleFixedExpenseStatusUseCase.execute(userId, id, active);
        return ResponseEntity.ok(response);
    }
}

