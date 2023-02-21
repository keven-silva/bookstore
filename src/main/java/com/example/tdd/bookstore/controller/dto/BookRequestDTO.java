package com.example.tdd.bookstore.controller.dto;

import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record BookRequestDTO(
    @NotBlank
    String title,
    @NotBlank
    String author,
    @NotBlank
    BookStatusEnum status,
    @Valid
    PersonRequestDTO personRequestDTO
) {
    public BookRequestDTO(Book book) {
        this(book.getTitle(), book.getAuthor(), book.getStatus(), null);
    }
}
