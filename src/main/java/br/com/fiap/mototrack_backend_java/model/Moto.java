package br.com.fiap.mototrack_backend_java.model;

import br.com.fiap.mototrack_backend_java.model.enums.ModeloMoto;
import br.com.fiap.mototrack_backend_java.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "mt_motos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moto_seq")
    @SequenceGenerator(name = "moto_seq", sequenceName = "MOTO_SEQ", allocationSize = 1)
    @Column(name = "id_moto")
    @JsonProperty("id_moto")
    private Long id;

    @Column(nullable = false, length = 7, unique = true)
    private String placa;

    @Column(nullable = false, length = 17, unique = true)
    private String chassi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 11)
    private ModeloMoto modelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;

    @OneToMany(mappedBy = "moto", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @OrderBy("dataMovimentacao ASC")
    private List<Movimentacao> movimentacoes;

    @OneToMany(mappedBy = "moto", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    @OrderBy("dataAlerta ASC")
    private List<Alerta> alertas;

}
