package com.example.tdd.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.example.tdd.bookstore.controller.dto.PersonRequestDTO;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_email")
    private String email;
    
    @Column(name = "person_cpf", unique = true)
    private String cpf;

    public Person(PersonRequestDTO PersonRequestDTO) {
        this.name = PersonRequestDTO.name();
        this.email = PersonRequestDTO.email();
        this.cpf = PersonRequestDTO.cpf();
    }
}