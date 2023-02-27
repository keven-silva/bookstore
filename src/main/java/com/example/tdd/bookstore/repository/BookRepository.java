package com.example.tdd.bookstore.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tdd.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Page<Book> findAll(Pageable pagination);

    public Optional<Book> findById(Long id);

    public Book findByTitle(String name);

    public void deleteByTitle(String name);

    public Book findByAuthor(String author);
}
