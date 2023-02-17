package com.example.tdd.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.service.BookService;

import jakarta.validation.Valid;

@RestController
public class BookController {
    
    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @PostMapping("/book")
    public ResponseEntity<Object> registerBook(@RequestBody @Valid BookRequestDTO book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookService.registerBook(book));
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid BookRequestDTO book) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                this.bookService.updateBook(id, book)
            );
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable Long id) {
        this.bookService.deleteBook(id);
    }
}
