package com.backend.hackaton.customer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private Long userId;
    private String name;
    private String contactNumber;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

