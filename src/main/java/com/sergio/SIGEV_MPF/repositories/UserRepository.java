package com.sergio.SIGEV_MPF.repositories;

import com.sergio.SIGEV_MPF.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Buscar usuário por e-mail
    Optional<User> findByEmail(String email);

    // Verificar se já existe usuário com determinado e-mail
    boolean existsByEmail(String email);

    // Verificar se já existe usuário com determinado CPF
    boolean existsByCpf(String cpf);
}
