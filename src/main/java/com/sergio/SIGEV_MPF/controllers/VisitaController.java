package com.sergio.SIGEV_MPF.controllers;

import com.sergio.SIGEV_MPF.domain.user.User;
import com.sergio.SIGEV_MPF.domain.visita.Visita;
import com.sergio.SIGEV_MPF.domain.visitante.Visitante;
import com.sergio.SIGEV_MPF.dto.VisitaRequestDTO;
import com.sergio.SIGEV_MPF.dto.VisitaResponseDTO;
import com.sergio.SIGEV_MPF.dto.VisitaUpdateDTO;
import com.sergio.SIGEV_MPF.repositories.UserRepository;
import com.sergio.SIGEV_MPF.repositories.VisitaRepository;
import com.sergio.SIGEV_MPF.repositories.VisitanteRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/visitas")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaRepository visitaRepository;
    private final VisitanteRepository visitanteRepository;
    private final UserRepository userRepository;

    private VisitaResponseDTO toDTO(Visita v) {
        return new VisitaResponseDTO(
                v.getId_visita(),
                v.getVisitante().getNomeCompleto(),
                v.getRegistradoPor().getName(),
                v.getLocal_destino(),
                v.getTipo_visita(),
                v.getData_entrada(),
                v.getData_saida(),
                v.getMotivo(),
                v.getObservacoes(),
                v.getStatus(),
                v.getCracha_numero(),
                v.getCracha_temporario(),
                v.getFoto(),
                v.getCreated_at(),
                v.getUpdated_at()
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<VisitaResponseDTO>> getAll() {
        return ResponseEntity.ok(
                visitaRepository.findAll().stream().map(this::toDTO).toList()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return visitaRepository.findById(UUID.fromString(id))
                .map(v -> ResponseEntity.ok(toDTO(v)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<?> create(@Valid @RequestBody VisitaRequestDTO dto) {

        Visitante visitante = visitanteRepository.findById(dto.idVisitante())
                .orElse(null);
        if (visitante == null) return ResponseEntity.badRequest().body("Visitante não encontrado!");

        User user = userRepository.findById(dto.registradoPor())
                .orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("Usuário registrador não encontrado!");

        Visita visita = new Visita();
        visita.setVisitante(visitante);
        visita.setRegistradoPor(user);
        visita.setLocal_destino(dto.localDestino());
        visita.setTipo_visita(dto.tipoVisita());
        visita.setData_entrada(dto.dataEntrada());
        visita.setData_saida(dto.dataSaida());
        visita.setMotivo(dto.motivo());
        visita.setObservacoes(dto.observacoes());
        visita.setStatus(dto.status() != null ? dto.status() : "Em andamento");
        visita.setCracha_numero(dto.crachaNumero());
        visita.setCracha_temporario(dto.crachaTemporario());
        visita.setFoto(dto.foto());

        Visita saved = visitaRepository.save(visita);
        return ResponseEntity.ok(toDTO(saved));
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        if (!visitaRepository.existsById(uuid)) {
            return ResponseEntity.notFound().build();
        }
        visitaRepository.deleteById(uuid);
        return ResponseEntity.ok("Visita removida com sucesso!");
    }

    @PatchMapping("/{id}/entrada")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> registrarEntrada(@PathVariable UUID id) {
        return visitaRepository.findById(id).map(visita -> {
            visita.setData_entrada(new Timestamp(System.currentTimeMillis()));
            visita.setStatus("Em andamento");
            visita.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            visitaRepository.save(visita);
            return ResponseEntity.ok("Entrada registrada com sucesso!");
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/saida")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> registrarSaida(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return visitaRepository.findById(uuid).map(visita -> {
            visita.setData_saida(new Timestamp(System.currentTimeMillis()));
            visita.setStatus("Finalizada");
            visita.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            visitaRepository.save(visita);
            return ResponseEntity.ok("Saída registrada com sucesso!");
        }).orElse(ResponseEntity.notFound().build());
    }
}
