package com.backend.hackaton.user.application.usecase;

import com.backend.hackaton.security.JwtTokenProvider;
import com.backend.hackaton.user.application.dto.AuthResponse;
import com.backend.hackaton.user.application.dto.LoginRequest;
import com.backend.hackaton.user.application.exception.InvalidCredentialsException;
import com.backend.hackaton.user.domain.User;
import com.backend.hackaton.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginUserUseCase(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public AuthResponse execute(LoginRequest request) {
        // Buscar usuario por username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciales inválidas"));

        // Verificar si el usuario está activo
        if (!user.getActive()) {
            throw new InvalidCredentialsException("Usuario inactivo");
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciales inválidas");
        }

        // Generar token JWT
        String token = jwtTokenProvider.generateToken(user.getUsername());

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}

