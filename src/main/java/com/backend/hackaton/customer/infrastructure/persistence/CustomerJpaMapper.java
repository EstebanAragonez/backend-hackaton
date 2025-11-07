package com.backend.hackaton.customer.infrastructure.persistence;

import com.backend.hackaton.customer.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaMapper {

    public Customer toDomain(CustomerJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Customer.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .contactNumber(entity.getContactNumber())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public CustomerJpaEntity toEntity(Customer domain) {
        if (domain == null) {
            return null;
        }

        return CustomerJpaEntity.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .name(domain.getName())
                .contactNumber(domain.getContactNumber())
                .email(domain.getEmail())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}

