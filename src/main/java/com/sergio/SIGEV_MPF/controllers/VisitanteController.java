package com.sergio.SIGEV_MPF.controllers;

import com.sergio.SIGEV_MPF.domain.visitante.Visitante;
import com.sergio.SIGEV_MPF.dto.VisitanteRequestDTO;
import com.sergio.SIGEV_MPF.dto.VisitanteResponseDTO;
import com.sergio.SIGEV_MPF.dto.VisitanteUpdateDTO;
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
@RequestMapping("/visitante")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteRepository visitanteRepository;

    // ✅ GET /visitante -> lista todos (ADMIN ou USER)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<Visitante>> getAllVisitantes() {
        return ResponseEntity.ok(visitanteRepository.findAll());
    }

    // ✅ GET /visitante/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getVisitanteById(@PathVariable UUID id) {
        return visitanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ POST /visitante -> cria novo visitante (somente ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createVisitante(@Valid @RequestBody VisitanteRequestDTO dto) {

        if (visitanteRepository.existsByCpf(dto.cpf())) {
            return ResponseEntity.badRequest().body("Erro: CPF já cadastrado!");
        }

        Visitante visitante = new Visitante();
        visitante.setNomeCompleto(dto.nomeCompleto());
        visitante.setCpf(dto.cpf());
        visitante.setTelefone(dto.telefone());
        visitante.setSexo(dto.sexo());
        visitante.setDocumentoIdentidade(dto.documentoIdentidade());
        visitante.setDataNascimento(java.sql.Date.valueOf(dto.dataNascimento()));
        visitante.setFoto(dto.foto());

        Visitante saved = visitanteRepository.save(visitante);

        VisitanteResponseDTO response = new VisitanteResponseDTO(
                saved.getId(),
                saved.getNomeCompleto(),
                saved.getCpf(),
                saved.getTelefone(),
                saved.getSexo(),
                saved.getDocumentoIdentidade(),
                saved.getDataNascimento(),
                saved.getFoto(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
        );

        return ResponseEntity.ok(response);
    }

    // ✅ PUT /visitante/{id} -> atualização completa
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVisitanteFull(
            @PathVariable UUID id,
            @Valid @RequestBody VisitanteRequestDTO dto) {

        return visitanteRepository.findById(id).map(visitante -> {

            if (!visitante.getCpf().equals(dto.cpf()) &&
                    visitanteRepository.existsByCpf(dto.cpf())) {
                return ResponseEntity.badRequest().body("Erro: CPF já está em uso!");
            }

            visitante.setNomeCompleto(dto.nomeCompleto());
            visitante.setCpf(dto.cpf());
            visitante.setDocumentoIdentidade(dto.documentoIdentidade());
            visitante.setSexo(dto.sexo());
            visitante.setDataNascimento(java.sql.Date.valueOf(dto.dataNascimento()));
            visitante.setTelefone(dto.telefone());
            visitante.setFoto(dto.foto());
            visitante.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Visitante updated = visitanteRepository.save(visitante);

            return ResponseEntity.ok(new VisitanteResponseDTO(
                    updated.getId(),
                    updated.getNomeCompleto(),
                    updated.getCpf(),
                    updated.getTelefone(),
                    updated.getSexo(),
                    updated.getDocumentoIdentidade(),
                    updated.getDataNascimento(),
                    updated.getFoto(),
                    updated.getCreatedAt(),
                    updated.getUpdatedAt()
            ));

        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ PATCH /visitante/{id} -> atualização parcial
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVisitantePartial(
            @PathVariable UUID id,
            @RequestBody VisitanteUpdateDTO dto) {

        return visitanteRepository.findById(id).map(visitante -> {

            if (dto.getCpf() != null && !visitante.getCpf().equals(dto.getCpf())) {
                if (visitanteRepository.existsByCpf(dto.getCpf())) {
                    return ResponseEntity.badRequest().body("Erro: CPF já está em uso!");
                }
                visitante.setCpf(dto.getCpf());
            }

            if (dto.getNomeCompleto() != null) {
                visitante.setNomeCompleto(dto.getNomeCompleto());
            }

            if (dto.getDocumentoIdentidade() != null) {
                visitante.setDocumentoIdentidade(dto.getDocumentoIdentidade());
            }

            if (dto.getSexo() != null) {
                visitante.setSexo(dto.getSexo());
            }

            if (dto.getDataNascimento() != null) {
                visitante.setDataNascimento(java.sql.Date.valueOf(dto.getDataNascimento()));
            }

            if (dto.getTelefone() != null) {
                visitante.setTelefone(dto.getTelefone());
            }

            if (dto.getFoto() != null) {
                visitante.setFoto(dto.getFoto());
            }

            visitante.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            Visitante updated = visitanteRepository.save(visitante);

            return ResponseEntity.ok(new VisitanteResponseDTO(
                    updated.getId(),
                    updated.getNomeCompleto(),
                    updated.getCpf(),
                    updated.getTelefone(),
                    updated.getSexo(),
                    updated.getDocumentoIdentidade(),
                    updated.getDataNascimento(),
                    updated.getFoto(),
                    updated.getCreatedAt(),
                    updated.getUpdatedAt()
            ));

        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE /visitante/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVisitante(@PathVariable UUID id) {
        if (!visitanteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        visitanteRepository.deleteById(id);
        return ResponseEntity.ok("Visitante deletado com sucesso!");
    }
}
