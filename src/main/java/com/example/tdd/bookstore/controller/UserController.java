package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.tdd.bookstore.controller.dto.TokenReponseDTO;
import com.example.tdd.bookstore.controller.dto.UserCreateRequestDTO;
import com.example.tdd.bookstore.controller.dto.UserRequestDTO;
import com.example.tdd.bookstore.infra.security.TokenService;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        User user = new User(userCreateRequestDTO);
        this.userService.registerUser(userCreateRequestDTO);

        URI uri = uriComponentsBuilder.path("/user/register").buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(uri).body(new UserCreateRequestDTO(user));
    }
    
    @GetMapping("/users")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> getAllPerson() {
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }
}
