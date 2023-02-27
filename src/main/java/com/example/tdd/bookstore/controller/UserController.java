package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RequestParam;

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

    @CacheEvict(value = "listUsers", allEntries = true)
    @PostMapping("/user/register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        User user = new User(userCreateRequestDTO);
        this.userService.registerUser(userCreateRequestDTO);

        URI uri = uriComponentsBuilder.path("/user/register").buildAndExpand(user.getUsername()).toUri();

        return ResponseEntity.created(uri).body(new UserCreateRequestDTO(user));
    }
    
    @Cacheable(value = "listUsers")
    @GetMapping("/users")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> getAllPerson(@RequestParam int page, @RequestParam int size, @RequestParam String order) {
        Pageable pagination = PageRequest.of(page, size, Direction.ASC, order);

        return ResponseEntity.ok().body(this.userService.getAllUsers(pagination));
    }
}
