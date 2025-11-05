package com.sergio.SIGEV_MPF.domain.visita;

import com.sergio.SIGEV_MPF.domain.user.User;
import com.sergio.SIGEV_MPF.domain.visitante.Visitante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "visitas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id_visita;

    @ManyToOne
    @JoinColumn(name = "id_visitante", nullable = false)
    private Visitante visitante;

    @ManyToOne
    @JoinColumn(name = "registrado_por", nullable = false)
    private User registradoPor;

    private String local_destino;
    private String tipo_visita;
    private Timestamp data_entrada;
    private Timestamp data_saida;
    private String motivo;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private String status = "Em andamento";
    private String cracha_numero;
    private Boolean cracha_temporario = false;
    private String foto;

    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());

    @PrePersist
    protected void onCreate() {
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }
}
