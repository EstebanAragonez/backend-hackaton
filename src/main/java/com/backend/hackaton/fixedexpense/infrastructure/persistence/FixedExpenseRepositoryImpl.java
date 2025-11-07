package com.backend.hackaton.fixedexpense.infrastructure.persistence;

import com.backend.hackaton.fixedexpense.domain.FixedExpense;
import com.backend.hackaton.fixedexpense.domain.FixedExpenseRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FixedExpenseRepositoryImpl implements FixedExpenseRepository {

    private final FixedExpenseJpaRepository jpaRepository;
    private final FixedExpenseJpaMapper mapper;

    public FixedExpenseRepositoryImpl(FixedExpenseJpaRepository jpaRepository, FixedExpenseJpaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public FixedExpense save(FixedExpense fixedExpense) {
        FixedExpenseJpaEntity entity = mapper.toEntity(fixedExpense);
        FixedExpenseJpaEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<FixedExpense> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<FixedExpense> findByUserId(Long userId) {
        return jpaRepository.findByUserId(userId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<FixedExpense> findByUserIdAndActive(Long userId, Boolean active) {
        return jpaRepository.findByUserIdAndActive(userId, active).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Boolean existsByIdAndUserId(Long id, Long userId) {
        return jpaRepository.existsByIdAndUserId(id, userId);
    }

    @Override
    public BigDecimal sumAmountByUserIdAndActive(Long userId, Boolean active) {
        BigDecimal result = jpaRepository.sumAmountByUserIdAndActive(userId, active);
        return result != null ? result : BigDecimal.ZERO;
    }
}

