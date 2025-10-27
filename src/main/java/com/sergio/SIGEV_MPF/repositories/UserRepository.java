package com.sergio.SIGEV_MPF.repositories;

import com.sergio.SIGEV_MPF.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
