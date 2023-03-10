package com.example.tdd.bookstore.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.test.context.ActiveProfiles;

@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PersonTest {
    
    private Person person;

    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setEmail("joao@example.com");
        person.setName("joão");
        person.setCpf("14551518");
    }
    
    @Test
    public void testInstaceOfPerson() {

        Assertions.assertThat(person)
            .isInstanceOf(Person.class);
    }

    @Test
    public void testNotInstaceOfPerson() {

        Assertions.assertThat(person)
            .isNotInstanceOf(Book.class);
    }
}
