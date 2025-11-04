package com.sergio.SIGEV_MPF.domain.visitante;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "visitantes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_visitante", updatable = false, nullable = false)
    private UUID id;  // ✅ use UUID e padronize o nome como "id"

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(unique = true, nullable = false)
    private String cpf;

    private String documentoIdentidade;
    private String sexo;
    private Date dataNascimento;
    private String telefone;
    private String foto;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
