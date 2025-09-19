package br.com.fiap.mototrack_backend_java.model;

import br.com.fiap.mototrack_backend_java.model.enums.TipoGravidade;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "mt_alertas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    @SequenceGenerator(name = "alerta_seq", sequenceName = "SEQ_MT_ALERTAS", allocationSize = 1)
    @Column(name = "id_alerta")
    @JsonProperty("id_alerta")
    private Long id;

    @Column(nullable = false, length = 200)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGravidade gravidade;

    @Column(name = "data_alerta", nullable = false)
    private LocalDateTime dataAlerta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id")
    private Moto moto;

    @PrePersist
    protected void onCreate() {
        if (this.dataAlerta == null) {
            this.dataAlerta = LocalDateTime.now();
        }
    }

}
