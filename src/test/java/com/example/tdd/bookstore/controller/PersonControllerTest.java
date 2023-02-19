package com.example.tdd.bookstore.controller;

import java.net.URI;

import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.tdd.bookstore.repository.PersonRepository;
import com.example.tdd.bookstore.model.Person;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PersonControllerTest {
    
    private PersonRequestDTO personRequestDTO;
    private  Gson gson;
    private String json;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PersonRepository personRepository;

    @BeforeAll
    public void setup() {
        gson = new Gson();
        
        personRequestDTO = new PersonRequestDTO(
            "John Doe",
            "john.doe@example.com",
            "111.111.111-11"
        );
        
       json = gson.toJson(personRequestDTO);
    }

    @Test
    public void testError404PersonController() throws Exception {
        URI uri = new URI("/");
 
        this.mockMvc.perform(
            MockMvcRequestBuilders
            .post(uri)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(404)
        );
    }

    @Test
    public void testRegisterPersonController() throws Exception {
        URI uri = new URI("/person");

        this.mockMvc.perform(
            MockMvcRequestBuilders
            .post(uri)
            .content(json.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(201)
        );
    }

    @Test
    public void testGetAllPersonController() throws Exception {
        URI uri = new URI("/persons");

        mockMvc.perform(
            MockMvcRequestBuilders
            .get(uri))
            .andExpect(
              MockMvcResultMatchers
              .status()
              .is(200)
            );
    }

    @Test
    public void testUpdatePersonController() throws Exception {
        URI uri = new URI("/person/1");
        
        this.mockMvc.perform(
            MockMvcRequestBuilders
            .put(uri)
            .content(json.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(200)
        );
    }

    @Test
    public void testDeletePersonController() throws Exception {
        Person person = new Person();
        person.setEmail("@example.com");
        person.setName("Alberto");
        person.setCpf("14551518");

        this.personRepository.save(person);
        
        URI uri = new URI("/person/1");
        
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
