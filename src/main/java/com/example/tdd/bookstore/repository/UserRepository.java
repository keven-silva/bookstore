package com.example.tdd.bookstore.repository;

import com.example.tdd.bookstore.model.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(Long id);
    
    public UserDetails findByUsername(String username);

    public Page<User> findAll(Pageable pagination);

}
