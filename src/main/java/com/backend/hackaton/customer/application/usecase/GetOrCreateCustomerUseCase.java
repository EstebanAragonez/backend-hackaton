package com.backend.hackaton.customer.application.usecase;

import com.backend.hackaton.customer.application.dto.CreateCustomerRequest;
import com.backend.hackaton.customer.application.dto.CustomerResponse;
import com.backend.hackaton.customer.domain.Customer;
import com.backend.hackaton.customer.domain.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetOrCreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public GetOrCreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse execute(Long userId, CreateCustomerRequest request) {
        // Buscar si ya existe un cliente con el mismo número de contacto
        return customerRepository.findByUserIdAndContactNumber(userId, request.getContactNumber())
                .map(this::mapToResponse)
                .orElseGet(() -> {
                    // Si no existe, crear uno nuevo
                    Customer customer = Customer.builder()
                            .userId(userId)
                            .name(request.getName())
                            .contactNumber(request.getContactNumber())
                            .email(request.getEmail())
                            .build();

                    Customer savedCustomer = customerRepository.save(customer);
                    return mapToResponse(savedCustomer);
                });
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .userId(customer.getUserId())
                .name(customer.getName())
                .contactNumber(customer.getContactNumber())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}

