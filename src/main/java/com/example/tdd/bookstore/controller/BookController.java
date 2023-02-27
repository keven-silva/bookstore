package com.example.tdd.bookstore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.service.BookService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@SecurityRequirement(name = "bearer-key")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Cacheable(value = "listBooks")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok().body(this.bookService.getAllBooks());
    }

    @CacheEvict(value = "listBooks", allEntries = true)
    @PostMapping("/book")
    public ResponseEntity<BookRequestDTO> registerBook(@RequestBody @Valid BookRequestDTO bookRequestDTO, UriComponentsBuilder uriComponetsBuilder) {
        Book book = new Book(bookRequestDTO);
        this.bookService.registerBook(bookRequestDTO);

        URI uri = uriComponetsBuilder.path("/book/{id}").buildAndExpand(book.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new BookRequestDTO(book));
    }

    @CacheEvict(value = "listBooks", allEntries = true)
    @PutMapping("/book/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody @Valid BookRequestDTO book) {
        return ResponseEntity.ok().body(this.bookService.updateBook(id, book));
    }

    @CacheEvict(value = "listBooks", allEntries = true)
    @DeleteMapping("/book/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
