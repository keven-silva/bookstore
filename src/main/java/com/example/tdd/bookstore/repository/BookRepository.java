package com.example.tdd.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tdd.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findAll();

    public Optional<Book> findById(Long id);

    public Book findByTitle(String name);

    public void deleteByTitle(String name);

    public Book findByAuthor(String author);
}
