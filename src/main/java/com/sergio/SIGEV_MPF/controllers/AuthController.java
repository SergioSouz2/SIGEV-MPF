package com.sergio.SIGEV_MPF.controllers;


import com.sergio.SIGEV_MPF.domain.user.User;
import com.sergio.SIGEV_MPF.dto.LoginRequestDTO;
import com.sergio.SIGEV_MPF.dto.RegisterRequestDTO;
import com.sergio.SIGEV_MPF.dto.ResponseDTO;
import com.sergio.SIGEV_MPF.infra.security.TokenService;
import com.sergio.SIGEV_MPF.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// http://localhost:8080/auth/login
// http://localhost:8080/auth/register
// http://localhost:8080/users #POST
// http://localhost:8080/users #GET
// http://localhost:8080/users/{email} #GET
// http://localhost:8080/users/{id} #DELETE


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        User user = this.repository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token, user.getRole()));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setRole("ADMIN"); // define um role padrão

            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token, newUser.getRole()));
        }

        return ResponseEntity.badRequest().build();
    }

}
