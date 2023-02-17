package com.example.tdd.bookstore.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tdd.bookstore.model.Person;


public interface PersonRepository extends JpaRepository<Person, Long>{
    public List<Person> findAll();

    public Person findById(int id);

    public Optional<Person> findByCpf(String cpf);

    public Person findByName(String name);

    public void deleteById(int id);

    public void deleteByName(String name);
}
