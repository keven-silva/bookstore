package com.example.tdd.bookstore.controller.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.tdd.bookstore.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequestDTO(
    @NotBlank
    String username,
    @NotBlank @Pattern(regexp = "\\X{6}")
    String password, 
    @NotBlank @Email
    String email
) {
    public UserCreateRequestDTO(User user) {
        this(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public String encodePassword() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
