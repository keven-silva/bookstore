package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.tdd.bookstore.controller.dto.person.PersonRequestDTO;
import com.example.tdd.bookstore.controller.dto.person.PersonResponseDTO;
import com.example.tdd.bookstore.service.PersonService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/persons")
@SecurityRequirement(name = "bearer-key")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<Page<PersonResponseDTO>> getAllPersons(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        Pageable pagination = PageRequest.of(page, size, Direction.ASC, sort);

        return ResponseEntity.ok().body(this.personService.getAllPersons(pagination));
    }

    @PostMapping
    public ResponseEntity<PersonResponseDTO> save(@RequestBody @Valid PersonRequestDTO personRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        PersonResponseDTO person = this.personService.registerPerson(personRequestDTO);

        URI uri = uriComponentsBuilder.path("/persons/{id}").buildAndExpand(person.getId()).toUri();

        return ResponseEntity.created(uri).body(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> update(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO PersonRequestDTO) {
        return ResponseEntity.ok().body(this.personService.updatePerson(id, PersonRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonResponseDTO> delete(@PathVariable Long id) {
        this.personService.deletePersonById(id);

        return ResponseEntity.noContent().build();
    }
}
