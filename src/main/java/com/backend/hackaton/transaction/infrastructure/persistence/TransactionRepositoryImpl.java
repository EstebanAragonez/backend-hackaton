package com.backend.hackaton.transaction.infrastructure.persistence;

import com.backend.hackaton.transaction.domain.Transaction;
import com.backend.hackaton.transaction.domain.TransactionRepository;
import com.backend.hackaton.transaction.domain.TransactionType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionJpaMapper mapper;

    public TransactionRepositoryImpl(TransactionJpaRepository jpaRepository, TransactionJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionJpaEntity entity = mapper.toEntity(transaction);
        TransactionJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Transaction> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndType(Long userId, TransactionType type) {
        return jpaRepository.findByUserIdAndType(userId, type).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndTransactionDateBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        return jpaRepository.findByUserIdAndTransactionDateBetween(userId, start, end).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByUserIdAndProductId(Long userId, Long productId) {
        return jpaRepository.findByUserIdAndProductId(userId, productId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal sumAmountByUserIdAndType(Long userId, TransactionType type) {
        BigDecimal result = jpaRepository.sumAmountByUserIdAndType(userId, type);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal sumAmountByUserIdAndTypeAndDateBetween(Long userId, TransactionType type, LocalDateTime start, LocalDateTime end) {
        BigDecimal result = jpaRepository.sumAmountByUserIdAndTypeAndDateBetween(userId, type, start, end);
        return result != null ? result : BigDecimal.ZERO;
    }
}

