package com.example.tdd.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.repository.PersonRepository;

import static org.mockito.Mockito.when;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class PersonServiceTest {
    
    Person person;
    PersonRequestDTO personDTO;
    
    @Mock
    private PersonRepository personRepository;
    
    @Spy
    @InjectMocks
    private PersonService personService;

    @BeforeAll
    public void setUp() {
        person = new Person();
        person.setEmail("@example.com");
        person.setName("Alberto");
        person.setCpf("14551518");
    }
    
    @Test
    public void testGetNullPersonByName() {
        String name = "teste";

        when(this.personRepository.findByName(name)).thenReturn(null);

        Assertions.assertThat(this.personService.getPersonName(name))
            .isNull();
    }
    
    @Test
    public void testInstaceofPerson() {
        String name = person.getName();

        when(this.personRepository.findByName(name)).thenReturn(person);

        Assertions.assertThat(this.personService.getPersonName(name))
        .isInstanceOf(Person.class);
    }
    
    @Test
    public void testRegisterPerson() {
        personDTO = new PersonRequestDTO(
            "Person Service",
            person.getEmail(),
            "12345568954"
        );

        when(this.personService.registerPerson(personDTO)).thenReturn(person);

        Assertions.assertThat(this.personService.registerPerson(personDTO))
            .isInstanceOf(Person.class);
    }

    @Test
    public void testGetAllPersons() {
        when(this.personRepository.findAll()).thenReturn(List.of(person));

        Assertions.assertThat(this.personService.getAllPersons())
            .isNotNull();
    }
    
    @Test
    public void testDeletePerson() {
        this.personService.save(person);
        this.personService.deletePerson(2L);

        Assertions.assertThat(this.personRepository.existsById(2L))
            .isFalse();
    }
}

