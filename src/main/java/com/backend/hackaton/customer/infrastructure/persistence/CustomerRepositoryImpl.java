package com.backend.hackaton.customer.infrastructure.persistence;

import com.backend.hackaton.customer.domain.Customer;
import com.backend.hackaton.customer.domain.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;
    private final CustomerJpaMapper mapper;

    public CustomerRepositoryImpl(CustomerJpaRepository jpaRepository, CustomerJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Customer save(Customer customer) {
        CustomerJpaEntity entity = mapper.toEntity(customer);
        CustomerJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Customer> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> findByUserIdAndContactNumber(Long userId, String contactNumber) {
        return jpaRepository.findByUserIdAndContactNumber(userId, contactNumber)
                .map(mapper::toDomain);
    }

    @Override
    public Boolean existsByIdAndUserId(Long id, Long userId) {
        return jpaRepository.existsByIdAndUserId(id, userId);
    }
}

