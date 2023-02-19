package com.example.tdd.bookstore.controller.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record UserCreateRequestDTO(
    String username, 
    String password, 
    String email
) {
    public String encodePassword() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
