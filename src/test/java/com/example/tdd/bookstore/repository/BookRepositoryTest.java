package com.example.tdd.bookstore.repository;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    Book book;

    @SpyBean
    private BookRepository bookRepository;
    
    @BeforeAll
    public void setUp() {
        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("j.k.Rowling");
        book.setStatus(BookStatusEnum.BORROWED);
    }

    @Test
    public void testNUllGetBookByTitle() {
        String name = "teste";

        when(this.bookRepository.findByTitle(name))
            .thenReturn(null);

        Assertions.assertThat(this.bookRepository.findByTitle(name))
            .isNull();
    }

    @Test
    public void testGetBookByTitle() {
        String name = "Harry Potter";

        when(this.bookRepository.findByTitle(name))
            .thenReturn(book);

        Assertions.assertThat(this.bookRepository.findByTitle(name))
            .isEqualTo(book);
    }
    
    @Test
    public void testRegisterBook() {
        when(this.bookRepository.save(book))
            .thenReturn(book);
        
        Assertions.assertThat(bookRepository.save(book))
            .isInstanceOf(Book.class);
    }

    @Test
    public void testUpdateBook() {
        String author = "j.k.Rowling";

        when(this.bookRepository.findByAuthor(author)).thenReturn(book);

        book.setTitle("Harry Potter e a camara secreta");
        
        Assertions.assertThat(this.bookRepository.findByAuthor(author).getAuthor()).isEqualTo(author);
    }

    @Test
    public void testDeletePerson() {
        String title = "Harry Potter";

        when(this.bookRepository.findByTitle(title))
            .thenReturn(null);

        this.bookRepository.deleteByTitle(title);
        
        Assertions.assertThat(this.bookRepository.findByTitle(title))
            .isNull();
    }

    @Test
    public void testGetAllBooks() {
        when(this.bookRepository.findAll())
            .thenReturn(List.of(book));

        Assertions.assertThat(this.bookRepository.findAll())
            .isNotNull();
    }

    @Test
    public void testGetBookById() {
        Long id = 1L;

        when(this.bookRepository.findById(id))
            .thenReturn(Optional.of(book));

        Assertions.assertThat(this.bookRepository.findById(id))
          .isEqualTo(Optional.of(book));
    }

    @Test
    public void testDeleteBookById() {
        Long id = 1L;

        when(this.bookRepository.findById(id))
            .thenReturn(null);

        this.bookRepository.delete(book);
        Assertions.assertThat(this.bookRepository.findById(id))
            .isNull();
    }
}
