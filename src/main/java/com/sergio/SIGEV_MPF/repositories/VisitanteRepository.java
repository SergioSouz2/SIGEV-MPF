package com.sergio.SIGEV_MPF.repositories;

import com.sergio.SIGEV_MPF.domain.visitante.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, UUID> {

    Optional<Visitante> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    Optional<Visitante> findByNomeCompleto(String nomeCompleto);
}
