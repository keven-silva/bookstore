package com.example.tdd.bookstore.controller.dto.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonResponseDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
}
