package com.example.tdd.bookstore.controller.dto.book;

import com.example.tdd.bookstore.controller.dto.person.PersonRequestDTO;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookResponseDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private BookStatusEnum status;
    @Valid
    private PersonRequestDTO personRequestDTO;

}
