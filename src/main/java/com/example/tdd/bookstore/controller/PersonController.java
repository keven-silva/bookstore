package com.example.tdd.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;
import com.example.tdd.bookstore.service.PersonService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@SecurityRequirement(name = "bearer-key")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public ResponseEntity<Object> getAllPerson() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                this.personService.getAllPersons()
            );
    }

    @PostMapping("/person")
    public ResponseEntity<Object> save(@RequestBody @Valid PersonRequestDTO PersonRequestDTO) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                this.personService.registerPerson(PersonRequestDTO)
            );
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid PersonRequestDTO PersonRequestDTO) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                this.personService.updatePerson(id, PersonRequestDTO)
            );
    }

    @DeleteMapping("/person/{id}")
    public void delete(@PathVariable Long id) {
        this.personService.deletePerson(id);
    }
}
