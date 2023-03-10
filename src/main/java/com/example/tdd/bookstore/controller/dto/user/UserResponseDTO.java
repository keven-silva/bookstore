package com.example.tdd.bookstore.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {
    private Long id;
    @NotBlank
    private String username;
    private String password; 
    @NotBlank @Email
    private String email;
}
