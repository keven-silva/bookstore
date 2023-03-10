package com.example.tdd.bookstore.controller.dto.user;

import com.example.tdd.bookstore.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequestDTO(
    @NotBlank
    String username,
    @NotBlank @Size(min = 6, max = 12)
    String password, 
    @NotBlank @Email
    String email
) {
    public UserCreateRequestDTO(User user) {
        this(user.getUsername(), user.getPassword(), user.getEmail());
    }
}
