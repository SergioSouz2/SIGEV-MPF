package com.sergio.SIGEV_MPF.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class VisitaUpdateDTO {
    private String localDestino;
    private String tipoVisita;
    private Timestamp dataEntrada;
    private Timestamp dataSaida;
    private String motivo;
    private String observacoes;
    private String status;
    private String crachaNumero;
    private Boolean crachaTemporario;
    private String foto;
}