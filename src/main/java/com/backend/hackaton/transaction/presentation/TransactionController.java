package com.backend.hackaton.transaction.presentation;

import com.backend.hackaton.shared.security.JwtUserExtractor;
import com.backend.hackaton.transaction.application.dto.TransactionRequest;
import com.backend.hackaton.transaction.application.dto.TransactionResponse;
import com.backend.hackaton.transaction.application.usecase.CreateTransactionUseCase;
import com.backend.hackaton.transaction.application.usecase.ListTransactionsUseCase;
import com.backend.hackaton.transaction.domain.TransactionType;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;
    private final ListTransactionsUseCase listTransactionsUseCase;
    private final JwtUserExtractor jwtUserExtractor;

    public TransactionController(
            CreateTransactionUseCase createTransactionUseCase,
            ListTransactionsUseCase listTransactionsUseCase,
            JwtUserExtractor jwtUserExtractor) {
        this.createTransactionUseCase = createTransactionUseCase;
        this.listTransactionsUseCase = listTransactionsUseCase;
        this.jwtUserExtractor = jwtUserExtractor;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        TransactionResponse response = createTransactionUseCase.execute(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        List<TransactionResponse> transactions = listTransactionsUseCase.execute(userId, type, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }
}

