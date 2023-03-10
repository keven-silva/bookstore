package com.example.tdd.bookstore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.controller.dto.user.UserCreateRequestDTO;
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
        Pageable pagination = PageRequest.of(1, 5, Direction.ASC,"id");
        Page<User> userPage = mock(Page.class);

        when(this.userRepository.findAll(any(Pageable.class)))
            .thenReturn(userPage);

        Assertions.assertThat(this.userService.getAllUsers(pagination))
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
