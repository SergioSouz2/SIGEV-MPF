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

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // GET /user/{email} -> busca usuário pelo email
    @GetMapping("/{email}")
    public ResponseEntity<Optional<User>> getUserByEmail(@PathVariable String email) {
        Optional<Optional<User>> user = Optional.ofNullable(userRepository.findByEmail(email));
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // POST /user -> cria um novo usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable String id,
            @Valid @RequestBody User userDetails) {

        // Buscar o usuário existente
        return userRepository.findById(id).map(user -> {
            // Atualiza os campos
            user.setName(userDetails.getName());
            user.setCpf(userDetails.getCpf());
            user.setDocumento_identidade(userDetails.getDocumento_identidade());
            user.setSexo(userDetails.getSexo());
            user.setData_nascimento(userDetails.getData_nascimento());
            user.setTelefone(userDetails.getTelefone());
            user.setEmail(userDetails.getEmail());
            user.setLogin(userDetails.getLogin());
            user.setSenha_hash(passwordEncoder.encode(userDetails.getSenha_hash()));
            user.setRole(userDetails.getRole());
            user.setUpdated_at(new java.sql.Timestamp(System.currentTimeMillis()));

            // Salva no banco
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }


    // DELETE /user/{id} -> deleta usuário por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
