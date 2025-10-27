package com.sergio.SIGEV_MPF.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private String name;
    private String cpf;
    private String documento_identidade;
    private String sexo;
    private Date data_nascimento;
    private String telefone;
    private String email;
    private String login;
    private String senha_hash;
    private String role;
    private Timestamp created_at;
    private Timestamp updated_at;


}
