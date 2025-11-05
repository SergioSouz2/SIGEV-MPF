package com.sergio.SIGEV_MPF.dto;

import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;


public record VisitaRequestDTO(
        @NotNull UUID idVisitante,
        @NotNull String registradoPor,
        String localDestino,
        String tipoVisita,
        Timestamp dataEntrada,
        Timestamp dataSaida,
        String motivo,
        String observacoes,
        String status,
        String crachaNumero,
        Boolean crachaTemporario,
        String foto
) {}
