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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.tdd.bookstore.controller.dto.person.PersonRequestDTO;
import com.example.tdd.bookstore.service.PersonService;

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
    private PersonService personService;
    
    
    @BeforeAll
    public void setup() {    
        gson = new Gson();
        
        personRequestDTO = new PersonRequestDTO(
            "John Doe",
            "john.doe@example.com",
            "11111111111"
        );

        json = gson.toJson(personRequestDTO);
    }


    @Test
    public void testError403PersonController() throws Exception {
        URI uri = new URI("/persons");

        this.mockMvc.perform(
            MockMvcRequestBuilders
            .post(uri)
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(403)
        );
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    public void testGetAllPersonController() throws Exception {
        URI uri = new URI("/persons");
        
        mockMvc.perform(
            MockMvcRequestBuilders
            .get(uri)
            .param("page", "1")
            .param("size", "10")
            .param("sort", "id"))
        .andExpect(
            MockMvcResultMatchers
            .status()
            .is(200)
        );
    }
    
    // @Test
    // @WithMockUser
    // public void testRegisterPersonController() throws Exception {
    //     URI uri = new URI("/persons");

    //     this.mockMvc.perform(
    //         MockMvcRequestBuilders
    //         .post(uri)
    //         .content(json)
    //         .contentType(MediaType.APPLICATION_JSON))
    //     .andExpect(
    //         MockMvcResultMatchers
    //         .status()
    //         .is(201)
    //     );
    // }
    
    @Test
    @WithMockUser
    public void testUpdatePersonController() throws Exception {
        Long id = 1L;
        
        URI uri = new URI("/persons/" + id);
        
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

    // @Test
    // @WithMockUser
    // public void testDeletePersonController() throws Exception {
    //     Long id = 1L;

    //     URI uri = new URI("/persons/" + id);
        
    //     this.mockMvc.perform(
    //         MockMvcRequestBuilders
    //         .delete(uri)
    //         .contentType(MediaType.APPLICATION_JSON))
    //     .andExpect(
    //         MockMvcResultMatchers
    //         .status()
    //         .is(200)
    //     );
    // }
}
