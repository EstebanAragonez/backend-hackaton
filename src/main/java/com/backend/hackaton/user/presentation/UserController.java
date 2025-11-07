package com.backend.hackaton.user.presentation;

import com.backend.hackaton.user.application.dto.AuthResponse;
import com.backend.hackaton.user.application.dto.LoginRequest;
import com.backend.hackaton.user.application.dto.RegisterUserRequest;
import com.backend.hackaton.user.application.dto.UserResponse;
import com.backend.hackaton.user.application.usecase.CreateUserUseCase;
import com.backend.hackaton.user.application.usecase.LoginUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, LoginUserUseCase loginUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = createUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = loginUserUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
}

