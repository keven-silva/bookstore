package com.example.tdd.bookstore.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tdd.bookstore.controller.dto.user.UserCreateRequestDTO;
import com.example.tdd.bookstore.controller.dto.user.UserResponseDTO;
import com.example.tdd.bookstore.infra.exceptions.ValidationsException;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userDTO = this.userRepository.save(user);
        
        return this.mapper.map(userDTO, UserResponseDTO.class);
    }

    public UserDetails getUser(String username) {
        return this.userRepository.findByUsername(username);
    }

    public UserResponseDTO getUserDetail(Long id) {
        User user = this.userRepository.findById(id).get();
        return this.mapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO registerUser(UserCreateRequestDTO userCreateRequestDTO) {
        User user = new User(userCreateRequestDTO);
        return this.save(user);
    }

    public Page<UserResponseDTO> getAllUsers(Pageable pagination) {
        return this.userRepository
            .findAll(pagination)
            .map(user -> this.mapper.map(user, UserResponseDTO.class));
    }

    public UserResponseDTO updateUser(Long userId, UserCreateRequestDTO userCreateRequestDTO) {
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
