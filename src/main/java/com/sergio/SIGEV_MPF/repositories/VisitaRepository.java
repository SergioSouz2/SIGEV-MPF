package com.sergio.SIGEV_MPF.repositories;

import com.sergio.SIGEV_MPF.domain.visita.Visita;
import com.sergio.SIGEV_MPF.domain.visitante.Visitante;
import com.sergio.SIGEV_MPF.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitaRepository extends JpaRepository<Visita, String> {

    List<Visita> findByVisitante(Visitante visitante);

    List<Visita> findByRegistradoPor(User user);

    List<Visita> findByStatus(String status);
}
