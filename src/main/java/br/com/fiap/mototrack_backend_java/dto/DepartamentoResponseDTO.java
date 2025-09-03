package br.com.fiap.mototrack_backend_java.dto;

import br.com.fiap.mototrack_backend_java.model.enums.TipoDepartamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id_departamento", "tipo_departamento", "descricao", "_links"})
public class DepartamentoResponseDTO extends RepresentationModel<DepartamentoResponseDTO> {

    @JsonProperty("id_departamento")
    private Long id;

    @JsonProperty("tipo_departamento")
    private TipoDepartamento tipo;

    private String descricao;

}
