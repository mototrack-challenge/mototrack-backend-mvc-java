package br.com.fiap.mototrack_backend_java.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "mt_movimentacoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimentacao_seq")
    @SequenceGenerator(name = "movimentacao_seq", sequenceName = "SEQ_MT_MOVIMENTACOES", allocationSize = 1)
    @Column(name = "id_movimentacao")
    @JsonProperty("id_movimentacao")
    private Long id;

    @Column(name = "data_movimentacao")
    private LocalDateTime dataMovimentacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id")
    private Moto moto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @PrePersist
    protected void onCreate() {
        if (this.dataMovimentacao == null) {
            this.dataMovimentacao = LocalDateTime.now();
        }
    }
}
