package com.example.tdd.bookstore.controller.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserLoginRequestDTO(
    @NotBlank @NotNull
    String username,
    @NotBlank @NotNull
    String password
) {
    
}
