package com.example.tdd.bookstore.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tdd.bookstore.controller.dto.person.PersonRequestDTO;
import com.example.tdd.bookstore.controller.dto.person.PersonResponseDTO;
import com.example.tdd.bookstore.infra.exceptions.ValidationsException;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public PersonResponseDTO save(Person person) {
        Person personDTO = this.personRepository.save(person);

        return this.mapper.map(personDTO, PersonResponseDTO.class);
    }

    public PersonResponseDTO getPersonByName(String name) {
        Person person = this.personRepository.findByName(name);
        return this.mapper.map(person, PersonResponseDTO.class);
    }

    public PersonResponseDTO getPersonByCpf(String cpf) {
        Person person = this.personRepository.findByCpf(cpf).get();
        return this.mapper.map(person, PersonResponseDTO.class);
    }

    public PersonResponseDTO registerPerson(PersonRequestDTO personRequestDTO) {
        Person person = new Person(personRequestDTO);
        return this.save(person);
    }

    public Page<PersonResponseDTO>getAllPersons(Pageable pagination) {
        return this.personRepository
            .findAll(pagination)
            .map(p -> this.mapper.map(p, PersonResponseDTO.class));
    }

    public PersonResponseDTO updatePerson(Long personId, PersonRequestDTO personRequestDTO) {
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
