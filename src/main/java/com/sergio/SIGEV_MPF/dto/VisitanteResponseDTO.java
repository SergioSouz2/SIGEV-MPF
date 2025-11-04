package com.sergio.SIGEV_MPF.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

public record VisitanteResponseDTO(
        UUID id,
        String nomeCompleto,
        String cpf,
        String telefone,
        String sexo,
        String documentoIdentidade,
        Date dataNascimento,
        String foto,
        Timestamp createdAt,
        Timestamp updatedAt
) {}
