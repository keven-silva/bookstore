package com.example.tdd.bookstore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tdd.bookstore.repository.BookRepository;

import jakarta.transaction.Transactional;

import com.example.tdd.bookstore.controller.dto.book.BookRequestDTO;
import com.example.tdd.bookstore.controller.dto.book.BookResponseDTO;
import com.example.tdd.bookstore.controller.dto.person.PersonResponseDTO;
import com.example.tdd.bookstore.infra.exceptions.ValidationsException;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.Person;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PersonService personService;

    public Page<BookResponseDTO> getAllBooks(Pageable pagination) {
        return this.bookRepository
            .findAll(pagination)
            .map(p -> this.mapper.map(p, BookResponseDTO.class));
    }

    @Transactional
    public BookResponseDTO save(Book book) {
        Book bookDTO = this.bookRepository.save(book);
        return this.mapper.map(bookDTO, BookResponseDTO.class);
    }

    public BookResponseDTO registerBook(BookRequestDTO bookCreateDTO) {
        Book book = new Book(bookCreateDTO);
        return this.save(book);
    }

    public BookResponseDTO getBookByTitle(String title) {
        Book book = this.bookRepository.findByTitle(title);
        return this.mapper.map(book, BookResponseDTO.class);
    }

    public BookResponseDTO updateBook(Long bookId, BookRequestDTO bookRequestDTO) {
        boolean bookExist = this.bookRepository.existsById(bookId);

        if(!bookExist){
            throw new ValidationsException("Book not found");
        }

        Book book = new Book(bookRequestDTO);
        book.setId(bookId);

        if(bookRequestDTO.personRequestDTO() == null){
            book.setPerson(null);
        }else {
            PersonResponseDTO personResponseDTO = this.personService.getPersonByCpf(bookRequestDTO.personRequestDTO().cpf());
            Person person = this.mapper.map(personResponseDTO, Person.class);
            
            book.setPerson(person);
        }
        
        return this.save(book);
    }

    @Transactional
    public void deleteBook(Long bookId)  {
        this.bookRepository.deleteById(bookId);
    }
}
