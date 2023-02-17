package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.tdd.bookstore.controller.dto.BookRequestDTO;
import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.model.Book;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;
import com.example.tdd.bookstore.repository.BookRepository;
import com.google.gson.Gson;

import org.springframework.http.MediaType;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class BookControllerTest {

    Book book;
    Person person;

    PersonRequestDTO PersonRequestDTO;
    BookRequestDTO bookCreateDTO;
    
    private  Gson gson;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setId(Long.valueOf(1));
        person.setEmail("joao@example.com");
        person.setName("joão");
        person.setCpf("145514518111");
        
        book = new Book();
        book.setId(Long.valueOf(1));
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setPerson(person);
        book.setStatus(BookStatusEnum.BORROWED);
        
        PersonRequestDTO = new PersonRequestDTO(
            person.getName(),
            person.getEmail(),
            person.getCpf()
        );

        bookCreateDTO = new BookRequestDTO(
            book.getTitle(),
            book.getAuthor(),
            book.getStatus(),
            null
            // PersonRequestDTO
        );
    }

    @Test
    public void testError404BookController() throws Exception {
        URI uri = new URI("/test");

        mockMvc.perform(
            MockMvcRequestBuilders
            .get(uri))
            .andExpect(
              MockMvcResultMatchers
              .status()
              .is(404)
            );
    }

    @Test
    public void testBookController() throws Exception {
        URI uri = new URI("/books");

        this.mockMvc.perform(
            MockMvcRequestBuilders
            .get(uri))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(200)
        );
    }

    @Test
    public void testRegisterBookController() throws Exception {
        URI uri = new URI("/book");

        gson = new Gson();

        String json = gson.toJson(bookCreateDTO);

        this.mockMvc.perform(
            MockMvcRequestBuilders
            .post(uri)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(201)
        );
    }

    @Test
    public void testUpdateBookController() throws Exception {
        URI uri = new URI("/book/1");

        gson = new Gson();

        String json = gson.toJson(bookCreateDTO);

        this.mockMvc.perform(
            MockMvcRequestBuilders
            .put(uri)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(200)
        );
    }

     
    @Test
    public void testDeleteBookController() throws Exception {
        Book bookDelete = new Book();
        bookDelete.setTitle("Harry Potter teste");
        bookDelete.setAuthor("J.K. Rowling");
        bookDelete.setStatus(BookStatusEnum.BORROWED);

        this.bookRepository.save(bookDelete);
        
        URI uri = new URI("/book/1");
        
        this.mockMvc.perform(
            MockMvcRequestBuilders
            .delete(uri)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(200)
        );
    }
}