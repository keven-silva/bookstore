package com.example.tdd.bookstore.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.infra.exceptions.ValidationsException;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {
    
    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Person save(Person person) {
        return this.personRepository.save(person);
    }

    public Person getPersonByName(String name) {
        return this.personRepository.findByName(name);
    }

    public Person getPersonByCpf(String cpf) {
        return this.personRepository.findByCpf(cpf).get();
    }

    public Person registerPerson(PersonRequestDTO personRequestDTO) {
        Person person = new Person(personRequestDTO);
        return this.save(person);
    }

    public Page<Person>getAllPersons(Pageable pagination) {
        return this.personRepository.findAll(pagination);
    }

    public Person updatePerson(Long personId, PersonRequestDTO personRequestDTO) {
        boolean personExist = this.personRepository.existsById(personId);

        if(!personExist) {
            throw new ValidationsException("Person not found");
        }
        
        Person person = new Person(personRequestDTO);
        person.setId(personId);
        
        return this.save(person);
    }

    @Transactional
    public void deletePersonById(Long personId)  {
            this.personRepository.deleteById(personId);
    }
}
