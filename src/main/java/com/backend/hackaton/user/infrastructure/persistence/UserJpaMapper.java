package com.backend.hackaton.user.infrastructure.persistence;

import com.backend.hackaton.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserJpaMapper {

    public User toDomain(UserJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .identityDocument(entity.getIdentityDocument())
                .dateOfBirth(entity.getDateOfBirth())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .active(entity.getActive())
                .build();
    }

    public UserJpaEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        return UserJpaEntity.builder()
                .id(domain.getId())
                .username(domain.getUsername())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .identityDocument(domain.getIdentityDocument())
                .dateOfBirth(domain.getDateOfBirth())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .active(domain.getActive())
                .build();
    }
}

