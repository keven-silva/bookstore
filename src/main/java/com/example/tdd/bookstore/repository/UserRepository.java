package com.example.tdd.bookstore.repository;

import com.example.tdd.bookstore.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    public UserDetails findByUsername(String username);

}
