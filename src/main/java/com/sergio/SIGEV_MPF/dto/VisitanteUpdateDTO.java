package com.sergio.SIGEV_MPF.dto;

import java.time.LocalDate;

public class VisitanteUpdateDTO {

    private String nomeCompleto;
    private String cpf;
    private String documentoIdentidade;
    private String sexo;
    private LocalDate dataNascimento;
    private String telefone;
    private String foto;

    // Getter e Setter para nomeCompleto
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    // Getter e Setter para cpf
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Getter e Setter para documentoIdentidade
    public String getDocumentoIdentidade() {
        return documentoIdentidade;
    }

    public void setDocumentoIdentidade(String documentoIdentidade) {
        this.documentoIdentidade = documentoIdentidade;
    }

    // Getter e Setter para sexo
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    // Getter e Setter para dataNascimento
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    // Getter e Setter para telefone
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Getter e Setter para foto
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
