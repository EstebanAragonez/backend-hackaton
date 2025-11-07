package com.backend.hackaton.user.application.usecase;

import com.backend.hackaton.user.application.dto.RegisterUserRequest;
import com.backend.hackaton.user.application.dto.UserResponse;
import com.backend.hackaton.user.application.exception.UserAlreadyExistsException;
import com.backend.hackaton.user.domain.User;
import com.backend.hackaton.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse execute(RegisterUserRequest request) {
        // Verificar si el username ya existe
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("El username ya está en uso");
        }

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("El email ya está en uso");
        }

        // Verificar si el documento de identidad ya existe
        if (userRepository.existsByIdentityDocument(request.getIdentityDocument())) {
            throw new UserAlreadyExistsException("El documento de identidad ya está en uso");
        }

        // Crear nuevo usuario
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .identityDocument(request.getIdentityDocument())
                .dateOfBirth(request.getDateOfBirth())
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .identityDocument(savedUser.getIdentityDocument())
                .dateOfBirth(savedUser.getDateOfBirth())
                .createdAt(savedUser.getCreatedAt())
                .active(savedUser.getActive())
                .build();
    }
}

