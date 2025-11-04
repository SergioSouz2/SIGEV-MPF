package com.sergio.SIGEV_MPF.controllers;

import com.sergio.SIGEV_MPF.domain.user.Role;
import com.sergio.SIGEV_MPF.domain.user.User;
import com.sergio.SIGEV_MPF.dto.LoginRequestDTO;
import com.sergio.SIGEV_MPF.dto.RegisterRequestDTO;
import com.sergio.SIGEV_MPF.dto.ResponseDTO;
import com.sergio.SIGEV_MPF.infra.security.TokenService;
import com.sergio.SIGEV_MPF.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        Optional<User> userOpt = repository.findByEmail(body.email());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(body.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Senha incorreta");
        }

        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new ResponseDTO(user.getName(), token, user.getRole().name()));
    }

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body) {
        Optional<User> existingUser = repository.findByEmail(body.email());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        newUser.setRole(Role.ADMIN);

        repository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token, newUser.getRole().name()));
    }
}
