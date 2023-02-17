package com.example.tdd.bookstore.repository;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    Book book;

    @Mock
    private BookRepository bookRepository;
    
    @BeforeAll
    public void setUp() {
        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("j.k.Rowling");
        book.setStatus(BookStatusEnum.BORROWED);
        this.bookRepository.save(book);
    }

    @Test
    public void testNUllFindPersonByName() {
        when(this.bookRepository.findByTitle("teste")).thenReturn(null);

        Assertions.assertThat(this.bookRepository.findByTitle("teste")).isNull();
    }

    @Test
    public void testRegisterPerson() {
        when(this.bookRepository.save(any(Book.class)))
            .thenReturn(book);
        
        Assertions.assertThat(bookRepository.save(book)).isEqualTo(book);
    }

    @Test
    public void testGetBookByName() {
        String name = "Harry Potter";

        when(this.bookRepository.findByTitle(name)).thenReturn(book);

        Assertions.assertThat(this.bookRepository.findByTitle(name)).isEqualTo(book);
    }

    @Test
    public void testUpdateBook() {
        String author = "j.k.Rowling";

        when(this.bookRepository.findByAuthor(author)).thenReturn(book);

        book.setTitle("Harry Potter e a camara secreta");
        
        Assertions.assertThat(this.bookRepository.findByAuthor(author).getAuthor()).isEqualTo(author);
    }

    // @Test
    // public void testDeletePerson() {
        
    //     this.bookRepository.deleteByTitle("Harry Potter");
        
    //     Assertions.assertEquals(0, bookRepository.findAll().size());
    // }
}
