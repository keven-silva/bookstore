package com.example.tdd.bookstore.controller;

import static org.mockito.Mockito.when;

import java.net.URI;

import com.google.gson.Gson;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.tdd.bookstore.repository.UserRepository;
import com.example.tdd.bookstore.service.PersonService;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;
import com.example.tdd.bookstore.infra.security.TokenService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PersonControllerTest {
    
    private PersonRequestDTO personRequestDTO;
    private UserCreateRequestDTO userDTO;
    
    private  Gson gson;
    private String json;
    
    private UserDetails userDetails;
    private User user;

    private String token; 

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Spy
    private PersonService personService;
    

    @BeforeAll
    public void setup() {
        gson = new Gson();
        
        personRequestDTO = new PersonRequestDTO(
            "John Doe",
            "john.doe@example.com",
            "111.111.111-11"
        );

        userDTO = new UserCreateRequestDTO(
            "John",
            "123456",
            "john.doe@example.com"
        );
        
        userDetails = this.userRepository.findByUsername(userDTO.username());
        
        if(userDetails != null){
         
            user = (User) userDetails;
            this.userRepository.delete(user);
        }

        json = gson.toJson(personRequestDTO);

    }
    @Test
    public void testError403PersonController() throws Exception {
        URI uri = new URI("/");

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
    public void testError404PersonController() throws Exception {
        URI uri = new URI("/");
        
        this.mockMvc.perform(
            MockMvcRequestBuilders
            .post(uri)
                .header("Authorization", "Bearer " + token)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers
                .status()
                .is(404)
        );
    }

    @Test
    public void testDeletePersonController() throws Exception {
        URI uri = new URI("/person/" + user.getId());
        
        this.mockMvc.perform(
            MockMvcRequestBuilders
            .delete(uri)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers
                .status()
                .is(200)
            );
    }

    @Test
    public void testUpdatePersonController() throws Exception {
        URI uri = new URI("/person/" + user.getId());
        
        this.mockMvc.perform(
            MockMvcRequestBuilders
            .put(uri)
                .header("Authorization", "Bearer " + token)
                .content(json.toString())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers
                .status()
                .is(200)
            );
    }

    @Test
    public void testGetAllPersonController() throws Exception {
        URI uri = new URI("/persons");
        
        mockMvc.perform(
            MockMvcRequestBuilders
            .get(uri)
                .header("Authorization", "Bearer " + token))
            .andExpect(
                MockMvcResultMatchers
                .status()
                .is(200)
            );
    }

    @Test
    public void testRegisterPersonController() throws Exception {
        URI uri = new URI("/person");

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

        token = tokenService.generateToken(user);
    }
}
