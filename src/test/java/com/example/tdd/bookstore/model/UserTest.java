package com.example.tdd.bookstore.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.test.context.ActiveProfiles;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserTest {
        
    private User user;

    @BeforeAll
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("John Doe");
        user.setPassword("123456");
        user.setEmail("john@example.com");
    }
    
    @Test
    public void testInstaceOfPerson() {

        Assertions.assertThat(user)
            .isInstanceOf(User.class);
    }

    @Test
    public void testNotInstaceOfPerson() {

        Assertions.assertThat(user)
            .isNotInstanceOf(Book.class);
    }
}
