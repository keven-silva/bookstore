package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;

import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.service.BookService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/books")
@SecurityRequirement(name = "bearer-key")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Cacheable(value = "listBooks")
    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam int page, @RequestParam int size, @RequestParam String order) {
        Pageable pagination = PageRequest.of(page, size, Direction.ASC, order);

        return ResponseEntity.ok().body(this.bookService.getAllBooks(pagination));
    }

    @CacheEvict(value = "listBooks", allEntries = true)
    @PostMapping
    public ResponseEntity<BookRequestDTO> registerBook(@RequestBody @Valid BookRequestDTO bookRequestDTO, UriComponentsBuilder uriComponetsBuilder) {
        Book book = this.bookService.registerBook(bookRequestDTO);

        URI uri = uriComponetsBuilder.path("/books/{id}").buildAndExpand(book.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new BookRequestDTO(book));
    }

    @CacheEvict(value = "listBooks", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody @Valid BookRequestDTO book) {
        return ResponseEntity.ok().body(this.bookService.updateBook(id, book));
    }

    @CacheEvict(value = "listBooks", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
