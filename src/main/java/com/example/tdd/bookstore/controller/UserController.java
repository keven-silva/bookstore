package com.example.tdd.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.example.tdd.bookstore.controller.dto.TokenReponseDTO;
import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;
import com.example.tdd.bookstore.controller.dto.UserRequestDTO;
import com.example.tdd.bookstore.infra.security.TokenService;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/user/login")
    public ResponseEntity<TokenReponseDTO> login(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequestDTO.username(), userRequestDTO.password());
        
        Authentication authentication = manager.authenticate(authenticationToken);
        
        String tokeJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenReponseDTO(tokeJwt));
    }

    @PostMapping("/user/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO) {
        System.out.println(userCreateRequestDTO);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                this.userService.registerUser(userCreateRequestDTO)
            );
    }
    
    @GetMapping("/users")
    public ResponseEntity<Object> getAllPerson() {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                this.userService.getAllUsers()
            );
    }
}
