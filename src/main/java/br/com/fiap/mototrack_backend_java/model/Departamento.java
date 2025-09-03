package br.com.fiap.mototrack_backend_java.model;

import br.com.fiap.mototrack_backend_java.model.enums.TipoDepartamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "mt_departamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamento_seq")
    @SequenceGenerator(name = "departamento_seq", sequenceName = "DEPARTAMENTO_SEQ", allocationSize = 1)
    @Column(name = "id_departamento")
    @JsonProperty("id_departamento")
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoDepartamento tipo;

    @OneToMany(mappedBy = "departamento")
    private List<Movimentacao> movimentacoes;
}
