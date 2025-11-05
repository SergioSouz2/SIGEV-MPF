package com.sergio.SIGEV_MPF.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record VisitaResponseDTO(
        UUID id,
        String visitanteNome,
        String registradoPorNome,
        String localDestino,
        String tipoVisita,
        Timestamp dataEntrada,
        Timestamp dataSaida,
        String motivo,
        String observacoes,
        String status,
        String crachaNumero,
        Boolean crachaTemporario,
        String foto,
        Timestamp createdAt,
        Timestamp updatedAt
) {}
