package com.example.tdd.bookstore.service;

import static org.mockito.Mockito.when;

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
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.repository.UserRepository;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
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
    public void testGetNullUserByUserame() throws Exception{
        String name = "teste";

        when(this.userRepository.findByUsername(name)).thenReturn(null);

        Assertions.assertThat(this.userService.getUser(name))
            .isNull();
    }
    
    @Test
    public void testInstaceofPerson() throws Exception {
        String name = user.getUsername();

        when(this.userRepository.findByUsername(name)).thenReturn(user);

        Assertions.assertThat(this.userService.getUser(name))
        .isInstanceOf(User.class);
    }
    
    // @Test
    // public void testRegisterPerson() throws Exception {
    //     userDTO = new UserCreateRequestDTO(
    //         "User Service",
    //         "12345568954",
    //         user.getEmail()
    //     );

    //     user = new User(userDTO);

    //     when(this.userRepository.save(user)).thenThrow(RuntimeException.class);

    //     Assertions.assertThatThrownBy(() -> this.userService.registerUser(userDTO))
    //         .isInstanceOf(Exception.class);
    // }

    @Test
    public void testGetAllPersons() throws Exception {
        when(this.userRepository.findAll()).thenReturn(List.of(user));

        Assertions.assertThat(this.userService.getAllUsers())
            .isNotNull();
    }
    
    @Test
    public void testDeletePerson() throws Exception {
        this.userService.save(user);
        this.userService.deleteUser(2L);

        Assertions.assertThat(this.userRepository.existsById(2L))
            .isFalse();
    }
}
