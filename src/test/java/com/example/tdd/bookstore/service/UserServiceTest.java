package com.example.tdd.bookstore.service;

import static org.mockito.ArgumentMatchers.any;
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

        when(this.userRepository.findByUsername(any(String.class)))
            .thenReturn(null);

        Assertions.assertThat(this.userService.getUser(name))
            .isNull();
    }
    
    @Test
    public void testGetUserByUsername() throws Exception {
        String name = user.getUsername();

        when(this.userRepository.findByUsername(any(String.class)))
            .thenReturn(user);

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

    //     User userPasswordEncoded = user; 
    //     userPasswordEncoded.setPassword(passwordEncoder.encode(user.getPassword()));
        
    //     when(this.userRepository.save(any(User.class)))
    //         .thenReturn(user);

    //     Assertions.assertThat(this.userService.registerUser(userDTO))
    //         .isInstanceOf(User.class);
    // }

    @Test
    public void testGetAllPersons() throws Exception {
        when(this.userRepository.findAll())
            .thenReturn(List.of(user));

        Assertions.assertThat(this.userService.getAllUsers())
            .isNotNull();
    }
    
    // @Test
    // public void testInstanceOfUpdateUser() throws Exception {
    //     userDTO = new UserCreateRequestDTO(
    //         "Person Service",
    //         user.getEmail(),
    //         "dev954"
    //     );

    //     when(this.userRepository.existsById(any(Long.class)))
    //         .thenReturn(true);

    //     when(this.userRepository.save(any(User.class)))
    //         .thenReturn(user);

    //     Assertions.assertThat(this.userService.updateUser(1L, userDTO))
    //         .isInstanceOf(User.class);
    // }

    // @Test
    // public void testDeletePerson() throws Exception {
    //     User userTest = this.userService.save(user);
    //     this.userService.deleteUser(userTest.getId());

    //     Assertions.assertThat(this.userRepository.existsById(2L))
    //         .isFalse();
    // }
}
