package com.example.tdd.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.repository.PersonRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PersonServiceTest {
    
    Person person;
    PersonRequestDTO personDTO;

    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setEmail("@example.com");
        person.setName("Alberto");
        person.setCpf("14551518");
        
        this.personService.save(person);
    }

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testGetNullPerson() {

        Assertions.assertNull(this.personService.getPersonName("teste"));
    }
    
    @Test
    public void testGetPersonName() {
        

        Assertions.assertNotNull(this.personService.getPersonName(person.getName()));
    }
    
    @Test
    public void testRegisterPerson() {
        personDTO = new PersonRequestDTO(
            "Person Service",
            person.getEmail(),
            "12345568954"
        );

        Assertions.assertNotNull(this.personService.registerPerson(personDTO));
    }

    @Test
    public void testGetAllPersons() {
        Assertions.assertNotNull(this.personService.getAllPersons());
    }
    
    @Test
    public void testInstaceofPerson() {
        
        Assertions.assertInstanceOf(Person.class, this.personService.getPersonName(person.getName()));
    }

    @Test
    public void testDeletePerson() {
        this.personService.save(person);
        this.personService.deletePerson(2L);

        Assertions.assertFalse(this.personRepository.existsById(2L));
    }
}

