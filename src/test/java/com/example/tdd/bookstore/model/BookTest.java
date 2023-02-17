package com.example.tdd.bookstore.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.model.enums.BookStatusEnum;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class BookTest {
    Book book;
    Person person;

    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setEmail("joao@example.com");
        person.setName("joão");
        person.setCpf("14551518");


        book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setPerson(person);
        book.setStatus(BookStatusEnum.BORROWED);
    }

    @Test
    public void testInstanceOfBook() {
        Assertions.assertInstanceOf(Book.class, book);
    }

    @Test
    public void testNotInstaceOfPerson() {

        Assertions.assertNotEquals(Object.class, book);
    }
}
