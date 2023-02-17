package com.example.tdd.bookstore.service;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;
import com.example.tdd.bookstore.repository.BookRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
// @ActiveProfiles("test")
public class BookServiceTest {
    
    Book book;
    Person person;

    PersonRequestDTO PersonRequestDTO;
    BookRequestDTO bookRequestDTO;

    @Mock
    private BookRepository bookRepository;
  
    @InjectMocks
    private BookService bookService;

    @InjectMocks
    private PersonService personService;

    @BeforeAll
    public void seUp() {
        
        person = new Person();
        person.setId(1L);
        person.setEmail("joao@example.com");
        person.setName("Book Service");
        person.setCpf("14555551518");

        // this.personService.save(person);

        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setStatus(BookStatusEnum.UNBORROWED);
        
        // this.bookService.save(book);
    }

    @Test
    public void testGetNullPersonByName() {
        String title = "Anjos e Demônios";

        when(this.bookRepository.findByTitle(title)).thenReturn(null);

        Assertions.assertThat(this.bookService.getBookByTitle(title)).isNull();
    }

    @Test
    public void testGetPersonByName() {
        String title = "Harry Potter";

        when(this.bookRepository.findByTitle(title)).thenReturn(book);

        Assertions.assertThat(this.bookService.getBookByTitle(title)).isInstanceOf(Book.class);
    }

    @Test
    public void testNotNullRegisterBook() {

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

        when(this.bookRepository.save(book)).thenReturn(bookTest);

        Assertions.assertThat(this.bookService.registerBook(bookRequestDTO)).isNull();
    }

    // @Test
    // public void testInstanceOfCreateBook() {
    //     PersonRequestDTO = new PersonRequestDTO(
    //         "Francisco",
    //         person.getEmail(),
    //         "21111151112"
    //     );

    //     bookRequestDTO = new BookRequestDTO(
    //         "Harry Potter e a pedra filosofal",
    //         book.getAuthor(),
    //         book.getStatus(),
    //         PersonRequestDTO
    //     );

    //     Assertions.assertInstanceOf(Book.class ,this.bookService.registerBook(bookRequestDTO));
    // }

    // @Test
    // public void testNullUpdateBook() {
    //     PersonRequestDTO = new PersonRequestDTO(
    //         "Francisco",
    //         person.getEmail(),
    //         "21111151112"
    //     );

    //     bookRequestDTO = new BookRequestDTO(
    //         "Harry Potter e  o calice de fogo",
    //         book.getAuthor(),
    //         book.getStatus(),
    //         PersonRequestDTO
    //     );

    //     Assertions.assertEquals(null, this.bookService.updateBook(0L, bookRequestDTO));
    // }

    // @Test
    // public void testInstanceofUpdateBook() {
    //     PersonRequestDTO = new PersonRequestDTO(
    //         "Francisco",
    //         person.getEmail(),
    //         "21111151112"
    //     );

    //     bookRequestDTO = new BookRequestDTO(
    //         "Harry Potter",
    //         book.getAuthor(),
    //         book.getStatus(),
    //         null
    //     );

    //     Assertions.assertInstanceOf(Book.class, this.bookService.updateBook(book.getId(), bookRequestDTO));
    // }

    // @Test
    // public void testDeleteBook() {
    //     book.setId(2L);
    //     this.bookService.save(book);

    //     this.bookService.deleteBook(2L);
    //     Assertions.assertEquals(false, this.bookRepository.existsById(2L));
    // }
}
