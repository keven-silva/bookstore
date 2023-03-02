package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
@RequestMapping("/persons")
@SecurityRequirement(name = "bearer-key")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @Cacheable(value = "listPersons")
    @GetMapping
    public ResponseEntity<Page<Person>> getAllPersons(@RequestParam int page, @RequestParam int size, @RequestParam String order) {
        Pageable pagination = PageRequest.of(page, size, Direction.ASC, order);

        return ResponseEntity.ok().body(this.personService.getAllPersons(pagination));
    }

    @CacheEvict(value = "listPersons", allEntries = true)
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PersonRequestDTO personRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        Person person = this.personService.registerPerson(personRequestDTO);

        URI uri = uriComponentsBuilder.path("/persons/{id}").buildAndExpand(person.getId()).toUri();

        return ResponseEntity.created(uri).body(new PersonRequestDTO(person));
    }

    @CacheEvict(value = "listPersons", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO PersonRequestDTO) {
        return ResponseEntity.ok().body(this.personService.updatePerson(id, PersonRequestDTO));
    }

    @CacheEvict(value = "listPersons", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        this.personService.deletePersonById(id);

        return ResponseEntity.noContent().build();
    }
}
