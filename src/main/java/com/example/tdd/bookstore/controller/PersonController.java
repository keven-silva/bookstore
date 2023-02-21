package com.example.tdd.bookstore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.model.Person;
import com.example.tdd.bookstore.service.PersonService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@SecurityRequirement(name = "bearer-key")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPerson() {
        return ResponseEntity.ok().body(this.personService.getAllPersons());
    }

    @PostMapping("/person")
    public ResponseEntity<Object> save(@RequestBody @Valid PersonRequestDTO personRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        Person person = new Person(personRequestDTO);
        this.personService.registerPerson(personRequestDTO);

        URI uri = uriComponentsBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri();

        return ResponseEntity.created(uri).body(new PersonRequestDTO(person));
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO PersonRequestDTO) {
        return ResponseEntity.ok().body(this.personService.updatePerson(id, PersonRequestDTO));
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        this.personService.deletePerson(id);

        return ResponseEntity.noContent().build();
    }
}
