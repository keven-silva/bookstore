package com.example.tdd.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;
import com.example.tdd.bookstore.infra.exceptions.ValidationsException;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return this.userRepository.save(user);
    }

    public UserDetails getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    public User registerUser(UserCreateRequestDTO userCreateRequestDTO) {
        User user = new User(userCreateRequestDTO);
        return this.save(user);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public UserDetails updatePerson(Long userId, UserCreateRequestDTO userCreateRequestDTO) {
        boolean personExist = this.userRepository.existsById(userId);

        if(!personExist) {
            throw new ValidationsException("UserDetails not found");
        }
        
        User user = new User(userCreateRequestDTO);
        user.setId(userId);
        
        return this.save(user);
    }

    @Transactional
    public void deleteUser(Long userId)  {
            this.userRepository.deleteById(userId);
    }
}
