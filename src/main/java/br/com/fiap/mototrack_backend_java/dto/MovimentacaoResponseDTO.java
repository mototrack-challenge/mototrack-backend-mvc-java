package br.com.fiap.mototrack_backend_java.dto;

import br.com.fiap.mototrack_backend_java.model.Departamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id_movimentacao", "moto_id", "departamento_id", "departamento_descricao", "data_movimentacao", "_links"})
public class MovimentacaoResponseDTO extends RepresentationModel<MovimentacaoResponseDTO> {

    @JsonProperty("id_movimentacao")
    private Long id;

    @JsonProperty("data_movimentacao")
    private LocalDateTime dataMovimentacao;

    @JsonProperty("moto_id")
    private Long idMoto;

    @JsonProperty("departamento_id")
    private Long idDepartamento;

    @JsonProperty("departamento_descricao")
    private String departamentoDescricao;
}
