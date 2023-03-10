package com.example.tdd.bookstore.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.tdd.bookstore.controller.dto.user.TokenReponseDTO;
import com.example.tdd.bookstore.controller.dto.user.UserCreateRequestDTO;
import com.example.tdd.bookstore.controller.dto.user.UserLoginRequestDTO;
import com.example.tdd.bookstore.controller.dto.user.UserResponseDTO;
import com.example.tdd.bookstore.infra.security.TokenService;
import com.example.tdd.bookstore.model.User;
import com.example.tdd.bookstore.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenReponseDTO> login(@RequestBody @Valid UserLoginRequestDTO userRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequestDTO.username(), userRequestDTO.password());
        
        Authentication authentication = manager.authenticate(authenticationToken);
        
        String tokeJwt = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenReponseDTO(tokeJwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserCreateRequestDTO userCreateRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        UserResponseDTO user = this.userService.registerUser(userCreateRequestDTO);

        URI uri = uriComponentsBuilder.path("/users/register/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(user);
    }
    
    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@RequestParam int page, @RequestParam int size, @RequestParam String sort) {
        Pageable pagination = PageRequest.of(page, size, Direction.ASC, sort);

        return ResponseEntity.ok().body(this.userService.getAllUsers(pagination));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<UserResponseDTO> getUserDetail(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.userService.getUserDetail(id));
    }
}
