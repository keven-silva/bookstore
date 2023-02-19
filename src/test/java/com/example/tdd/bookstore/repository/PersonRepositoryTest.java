package com.example.tdd.bookstore.repository;

import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.model.Person;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {
    
    private Person person;
    
    @SpyBean
    private PersonRepository personRepository;
    
    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setName("John");
        person.setEmail("joao@gmail.com");
        person.setCpf("025.254.612-45");
    }

    @Test
    public void testNUllGetPersonByName() {
        String name = "teste";

        when(this.personRepository.findByName(name))
            .thenReturn(null);
        
        Assertions.assertThat(this.personRepository.findByName(name))
            .isNull();
    }

    @Test
    public void testGetPersonByName() {
        String name = "John";

        when(this.personRepository.findByName(name))
            .thenReturn(person);
            
            Assertions.assertThat(this.personRepository.findByName(name).getName())
            .isEqualTo(name);
        }
    
    @Test
    public void testRegisterPerson() {
        when(this.personRepository.save(person))
        .thenReturn(person);
    
        Assertions.assertThat(personRepository.save(person))
            .isInstanceOf(Person.class);
    }

    @Test
    public void testUpdatePerson() {
        String name = "Jane";
        
        when(this.personRepository.findByName(name))
            .thenReturn(person);
        
        person.setEmail("jane@gmail.com");
        
        Assertions.assertThat(this.personRepository.findByName("Jane").getEmail())
            .isEqualTo(person.getEmail());
    }

    @Test
    public void testDeletePerson() {
        String name = "John";

        when(this.personRepository.findByName(name))
            .thenReturn(null);

        this.personRepository.deleteByName(name);
        
        Assertions.assertThat(personRepository.findByName(name))
            .isNull();
    }

    @Test
    public void testGetAllPersons() {
        when(this.personRepository.findAll())
            .thenReturn(List.of(person));

        Assertions.assertThat(this.personRepository.findAll())
            .isNotNull();
    }

    @Test
    public void testGetPersonById() {
        Long id = 1L;

        when(this.personRepository.findById(id))
            .thenReturn(Optional.of(person));

        Assertions.assertThat(this.personRepository.findById(id))
          .isEqualTo(Optional.of(person));
    }

    @Test
    public void testDeletePersonById() {
        Long id = 1L;

        when(this.personRepository.findById(id))
            .thenReturn(null);

        this.personRepository.delete(person);
        Assertions.assertThat(this.personRepository.findById(id))
            .isNull();
    }
}
