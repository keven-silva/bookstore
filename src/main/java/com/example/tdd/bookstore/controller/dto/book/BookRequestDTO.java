package com.example.tdd.bookstore.controller.dto.book;

import com.example.tdd.bookstore.controller.dto.person.PersonRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record BookRequestDTO(
    @NotBlank
    String title,
    @NotBlank
    String author,
    BookStatusEnum status,
    @Valid
    PersonRequestDTO personRequestDTO
) {
    public BookRequestDTO(Book book) {
        this(book.getTitle(), book.getAuthor(), book.getStatus(), null);
    }
}
