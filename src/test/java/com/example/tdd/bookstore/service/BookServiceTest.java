package com.example.tdd.bookstore.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;
import com.example.tdd.bookstore.repository.BookRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class BookServiceTest {
    
    Book book;
    Person person;

    PersonRequestDTO PersonRequestDTO;
    BookRequestDTO bookRequestDTO;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PersonService personService;
    
    @Spy
    @InjectMocks
    private BookService bookService;

    @BeforeAll
    public void seUp() {
        
        person = new Person();
        person.setId(1L);
        person.setEmail("joao@example.com");
        person.setName("Book Service");
        person.setCpf("14555551518");

        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setStatus(BookStatusEnum.UNBORROWED);
        
    }

    @Test
    public void testGetNullBookByTitle() throws Exception {
        String title = "Anjos e Demônios";

        when(this.bookRepository.findByTitle(any(String.class)))
            .thenReturn(null);
        
        Assertions.assertThat(this.bookService.getBookByTitle(title))
            .isNull();
    }

    @Test
    public void testGetBookByTitle() throws Exception {
        String title = "Harry Potter";

        when(this.bookRepository.findByTitle(any(String.class)))
            .thenReturn(book);

        Assertions.assertThat(this.bookService.getBookByTitle(title))
            .isInstanceOf(Book.class);
    }

    @Test
    public void testRegisterBook() throws Exception {

        Book bookTest = new Book();
        bookTest.setId(1L);
        bookTest.setTitle("Harry Potter");
        bookTest.setAuthor("J.K. Rowling");
        bookTest.setStatus(BookStatusEnum.UNBORROWED);

        PersonRequestDTO = new PersonRequestDTO(
            "João",
            person.getEmail(),
            "11111111111"
        );

        bookRequestDTO = new BookRequestDTO(
            "Harry Potter e a camara secreta",
            book.getAuthor(),
            book.getStatus(),
            PersonRequestDTO
        );

        when(this.bookRepository.save(any(Book.class)))
            .thenReturn(bookTest);

        Assertions.assertThat(this.bookService.registerBook(bookRequestDTO))
            .isInstanceOf(Book.class);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Pageable pagination = PageRequest.of(1, 5, Direction.ASC,"id");
        
        when(this.bookRepository.findAll())
            .thenReturn(List.of(book));
        
        Assertions.assertThat(this.bookService.getAllBooks(pagination))
            .isNotNull();
    }

    @Test
    public void testInstanceOfUpdateBook() throws Exception {
        PersonRequestDTO = new PersonRequestDTO(
            "Francisco",
            person.getEmail(),
            "21111151112"
        );

        bookRequestDTO = new BookRequestDTO(
            "Harry Potter e  o calice de fogo",
            book.getAuthor(),
            book.getStatus(),
            PersonRequestDTO
        );

        Long id = book.getId();

        when(this.personService.getPersonByCpf(any(String.class)))
            .thenReturn(person);

        when(this.bookRepository.existsById(any(Long.class)))
            .thenReturn(true);
        
        when(this.bookRepository.save(any(Book.class)))
            .thenReturn(book);

        Assertions.assertThat(this.bookService.updateBook(id, bookRequestDTO))
            .isInstanceOf(Book.class);
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long id = book.getId();
        this.bookService.save(book);

        this.bookService.deleteBook(id);
        Assertions.assertThat(this.bookRepository.existsById(id))
            .isFalse();
    }
}
