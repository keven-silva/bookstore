package com.example.tdd.bookstore.controller.dto.person;

import com.example.tdd.bookstore.model.Person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PersonRequestDTO(
    @NotBlank
    String name,
    @NotBlank @Email
    String email,
    @NotBlank @Pattern(regexp = "\\d{11}")
    String cpf
) {
    public PersonRequestDTO(Person person) {
        this(person.getName(), person.getEmail(), person.getCpf());
    }
}
