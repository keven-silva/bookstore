package com.example.tdd.bookstore.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.model.Person;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {
    
    private Person person;
    
    @Autowired
    private PersonRepository personRepository;
    
    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setName("John");
        person.setEmail("joao@gmail.com");
        person.setCpf("025.254.612-45");
        this.personRepository.save(person);
    }

    @Test
    public void testNUllFindPersonByName() {
        Assertions.assertNull(this.personRepository.findByName("teste"));
    }

    @Test
    public void testRegisterPerson() {
        
        Assertions.assertEquals(1, personRepository.findAll().size());
    }

    @Test
    public void testFindPersonByName() {

        Assertions.assertEquals("John", this.personRepository.findByName("John").getName());
    }

    @Test
    public void testUpdatePerson() {
        person.setName("Jane");
        this.personRepository.save(person);
        
        Assertions.assertEquals("Jane", this.personRepository.findByName("Jane").getName());
    }

    @Test
    public void testDeletePerson() {
        
        this.personRepository.deleteByName("John");
        
        Assertions.assertEquals(0, personRepository.findAll().size());
    }

    @Test
    public void testGetAllPersons() {
        Assertions.assertNotNull(this.personRepository.findAll());
    }

    @Test
    public void testGetPersonById() {
        Assertions.assertNotNull(this.personRepository.findById(person.getId()));
    }

    @Test
    public void testDeletePersonById() {
        this.personRepository.save(person);
        this.personRepository.delete(person);
        Assertions.assertEquals(Optional.empty(), this.personRepository.findById(person.getId()));
    }
}
