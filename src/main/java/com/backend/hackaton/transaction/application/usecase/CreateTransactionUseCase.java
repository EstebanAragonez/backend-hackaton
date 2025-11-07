package com.backend.hackaton.transaction.application.usecase;

import com.backend.hackaton.customer.application.dto.CreateCustomerRequest;
import com.backend.hackaton.customer.application.exception.CustomerNotFoundException;
import com.backend.hackaton.customer.application.usecase.GetOrCreateCustomerUseCase;
import com.backend.hackaton.customer.domain.CustomerRepository;
import com.backend.hackaton.product.application.exception.ProductInactiveException;
import com.backend.hackaton.product.application.exception.ProductNotFoundException;
import com.backend.hackaton.product.application.service.ProductStockService;
import com.backend.hackaton.product.domain.Product;
import com.backend.hackaton.product.domain.ProductRepository;
import com.backend.hackaton.transaction.application.dto.TransactionRequest;
import com.backend.hackaton.transaction.application.dto.TransactionResponse;
import com.backend.hackaton.transaction.domain.Transaction;
import com.backend.hackaton.transaction.domain.TransactionRepository;
import com.backend.hackaton.transaction.domain.TransactionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final ProductStockService productStockService;
    private final CustomerRepository customerRepository;
    private final GetOrCreateCustomerUseCase getOrCreateCustomerUseCase;

    public CreateTransactionUseCase(
            TransactionRepository transactionRepository,
            ProductRepository productRepository,
            ProductStockService productStockService,
            CustomerRepository customerRepository,
            GetOrCreateCustomerUseCase getOrCreateCustomerUseCase) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.productStockService = productStockService;
        this.customerRepository = customerRepository;
        this.getOrCreateCustomerUseCase = getOrCreateCustomerUseCase;
    }

    @Transactional
    public TransactionResponse execute(Long userId, TransactionRequest request) {
        Long customerId = null;

        // Si es una venta (INCOME), manejar el cliente
        if (request.getType() == TransactionType.INCOME) {
            // Si se proporciona customerId, verificar que existe y pertenece al usuario
            if (request.getCustomerId() != null) {
                customerId = customerRepository.findById(request.getCustomerId())
                        .map(customer -> {
                            if (!customer.getUserId().equals(userId)) {
                                throw new CustomerNotFoundException("Cliente no encontrado");
                            }
                            return customer.getId();
                        })
                        .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado"));
            }
            // Si se proporcionan datos del cliente, crear o obtener el cliente
            else if (request.getCustomer() != null) {
                CreateCustomerRequest customerRequest = new CreateCustomerRequest(
                        request.getCustomer().getName(),
                        request.getCustomer().getContactNumber(),
                        request.getCustomer().getEmail()
                );
                customerId = getOrCreateCustomerUseCase.execute(userId, customerRequest).getId();
            }

            // Si es una venta con producto, actualizar stock
            if (request.getProductId() != null && request.getQuantity() != null) {
                // Verificar que el producto existe y pertenece al usuario
                Product product = productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

                if (!product.getUserId().equals(userId)) {
                    throw new ProductNotFoundException("Producto no encontrado");
                }

                if (!product.getActive()) {
                    throw new ProductInactiveException("El producto no está disponible (está inactivo)");
                }

                // Actualizar stock y desactivar si llega a 0
                productStockService.updateStockAndCheckAvailability(request.getProductId(), request.getQuantity());
            }
        }

        // Crear la transacción
        Transaction transaction = Transaction.builder()
                .userId(userId)
                .type(request.getType())
                .description(request.getDescription())
                .amount(request.getAmount())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .customerId(customerId)
                .transactionDate(request.getTransactionDate() != null ? request.getTransactionDate() : LocalDateTime.now())
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
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

