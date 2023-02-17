package com.example.tdd.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tdd.bookstore.repository.BookRepository;
import com.example.tdd.bookstore.repository.PersonRepository;

import jakarta.transaction.Transactional;

import com.example.tdd.bookstore.config.ValidationsException;
import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.Person;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    @Transactional
    public Book save(Book book) {
        return this.bookRepository.save(book);
    }

    public Book registerBook(BookRequestDTO bookCreateDTO) {
        Book book = new Book(bookCreateDTO);
        return this.save(book);
    }

    public Book getBookByTitle(String title) {
        return this.bookRepository.findByTitle(title);
    }

    public Book updateBook(Long bookId, BookRequestDTO bookRequestDTO) {
        boolean bookExist = this.bookRepository.existsById(bookId);

        if(!bookExist){
            throw new ValidationsException("Book not found");
        }

        Book book = new Book(bookRequestDTO);
        book.setId(bookId);

        if(bookRequestDTO.personRequestDTO() == null){
            book.setPerson(null);
        }else {
            Person person = this.personRepository.findByCpf(bookRequestDTO.personRequestDTO().cpf()).get();
            book.setPerson(person);
        }
        
        return this.save(book);
    }

    @Transactional
    public void deleteBook(Long bookId)  {
        this.bookRepository.deleteById(bookId);
    }
}
