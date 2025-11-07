package com.backend.hackaton.shared.security;

import org.springframework.stereotype.Service;

@Service
public class JwtUserExtractor {

    private final CurrentUserService currentUserService;

    public JwtUserExtractor(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    public Long getCurrentUserId() {
        return currentUserService.getCurrentUserId();
    }
}

