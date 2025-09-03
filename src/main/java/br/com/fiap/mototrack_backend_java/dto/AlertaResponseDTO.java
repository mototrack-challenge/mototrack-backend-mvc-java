package br.com.fiap.mototrack_backend_java.dto;

import br.com.fiap.mototrack_backend_java.model.enums.TipoGravidade;
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
@JsonPropertyOrder({"id_alerta", "gravidade", "mensagem", "moto_id", "data_alerta", "_links"})
public class AlertaResponseDTO extends RepresentationModel<AlertaResponseDTO>  {

    @JsonProperty("id_alerta")
    private Long id;

    private TipoGravidade gravidade;

    private String mensagem;

    @JsonProperty("data_alerta")
    private LocalDateTime dataAlerta;

    @JsonProperty("moto_id")
    private Long idMoto;
}
