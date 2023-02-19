package com.example.tdd.bookstore.service;

import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.repository.UserRepository;

public class UserServiceTest {
    User user;
    UserCreateRequestDTO userDTO;
    
    @Mock
    private UserRepository userRepository;
    
    @Spy
    @InjectMocks
    private UserService userService;

    @BeforeAll
    public void setUp() {
        user = new User();
        user.setUsername("teste");
        user.setPassword("123456");
        user.setEmail("teste@example.com");
    }
    
    @Test
    public void testGetNullUserByUserame() {
        String name = "teste";

        when(this.userRepository.findByUsername(name)).thenReturn(null);

        Assertions.assertThat(this.userService.getUser(name))
            .isNull();
    }
    
    @Test
    public void testInstaceofPerson() {
        String name = user.getUsername();

        when(this.userRepository.findByUsername(name)).thenReturn(user);

        Assertions.assertThat(this.userService.getUser(name))
        .isInstanceOf(User.class);
    }
    
    @Test
    public void testRegisterPerson() {
        userDTO = new UserCreateRequestDTO(
            "User Service",
            "12345568954",
            user.getEmail()
        );

        when(this.userService.registerUser(userDTO)).thenReturn(user);

        Assertions.assertThat(this.userService.registerUser(userDTO))
            .isInstanceOf(User.class);
    }

    @Test
    public void testGetAllPersons() {
        when(this.userRepository.findAll()).thenReturn(List.of(user));

        Assertions.assertThat(this.userService.getAllUsers())
            .isNotNull();
    }
    
    @Test
    public void testDeletePerson() {
        this.userService.save(user);
        this.userService.deleteUser(2L);

        Assertions.assertThat(this.userRepository.existsById(2L))
            .isFalse();
    }
}
