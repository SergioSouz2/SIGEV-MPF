package com.sergio.SIGEV_MPF.controllers;

import com.sergio.SIGEV_MPF.domain.user.User;
import com.sergio.SIGEV_MPF.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ GET /user -> lista todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // ✅ GET /user/{email} -> busca usuário pelo e-mail
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST /user -> cria um novo usuário
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        // Verifica se o e-mail já existe
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Erro: Email já está em uso!");
        }

        // Se CPF foi informado, valida duplicidade
        if (user.getCpf() != null && !user.getCpf().isBlank()) {
            if (userRepository.existsByCpf(user.getCpf())) {
                return ResponseEntity.badRequest().body("Erro: CPF já está em uso!");
            }
        }

        // Criptografa a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Salva o usuário
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ PUT /user/{id} -> atualiza um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @Valid @RequestBody User userDetails) {

        return userRepository.findById(id).map(user -> {

            // Verifica se o e-mail novo já pertence a outro usuário
            if (!user.getEmail().equals(userDetails.getEmail())
                    && userRepository.existsByEmail(userDetails.getEmail())) {
                return ResponseEntity.badRequest().body("Erro: Email já está em uso por outro usuário!");
            }

            // Verifica CPF duplicado (se informado)
            if (userDetails.getCpf() != null && !userDetails.getCpf().isBlank()) {
                if (!userDetails.getCpf().equals(user.getCpf())
                        && userRepository.existsByCpf(userDetails.getCpf())) {
                    return ResponseEntity.badRequest().body("Erro: CPF já está em uso por outro usuário!");
                }
            }

            // Atualiza os campos
            user.setName(userDetails.getName());
            user.setCpf(userDetails.getCpf());
            user.setDocumento_identidade(userDetails.getDocumento_identidade());
            user.setSexo(userDetails.getSexo());
            user.setData_nascimento(userDetails.getData_nascimento());
            user.setTelefone(userDetails.getTelefone());
            user.setEmail(userDetails.getEmail());
            user.setLogin(userDetails.getLogin());
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            user.setRole(userDetails.getRole());
            user.setUpdated_at(new java.sql.Timestamp(System.currentTimeMillis()));

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE /user/{id} -> deleta usuário por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
