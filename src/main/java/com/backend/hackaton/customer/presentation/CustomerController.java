package com.backend.hackaton.customer.presentation;

import com.backend.hackaton.customer.application.dto.CreateCustomerRequest;
import com.backend.hackaton.customer.application.dto.CustomerResponse;
import com.backend.hackaton.customer.application.usecase.CreateCustomerUseCase;
import com.backend.hackaton.customer.application.usecase.ListCustomersUseCase;
import com.backend.hackaton.shared.security.JwtUserExtractor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final ListCustomersUseCase listCustomersUseCase;
    private final JwtUserExtractor jwtUserExtractor;

    public CustomerController(
            CreateCustomerUseCase createCustomerUseCase,
            ListCustomersUseCase listCustomersUseCase,
            JwtUserExtractor jwtUserExtractor) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.listCustomersUseCase = listCustomersUseCase;
        this.jwtUserExtractor = jwtUserExtractor;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        Long userId = jwtUserExtractor.getCurrentUserId();
        CustomerResponse response = createCustomerUseCase.execute(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomers() {
        Long userId = jwtUserExtractor.getCurrentUserId();
        List<CustomerResponse> customers = listCustomersUseCase.execute(userId);
        return ResponseEntity.ok(customers);
    }
}

