package com.backend.hackaton.customer.domain;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findByUserId(Long userId);
    Optional<Customer> findByUserIdAndContactNumber(Long userId, String contactNumber);
    Boolean existsByIdAndUserId(Long id, Long userId);
}

