package com.sergio.SIGEV_MPF.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDate;

public record VisitanteRequestDTO(
        @NotBlank(message = "Nome completo é obrigatório")
        String nomeCompleto,

        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotBlank(message = "Sexo é obrigatório")
        String sexo,

        String documentoIdentidade,
        LocalDate dataNascimento,
        String foto
) {}
