package br.com.fiap.mototrack_backend_java.dto;

import br.com.fiap.mototrack_backend_java.model.enums.TipoGravidade;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertaRequestDTO {

    @NotNull(message = "A gravidade do alerta é obrigatório")
    private TipoGravidade gravidade;

    @NotBlank(message = "A mensagem do alerta é obrigatória")
    @Size(max = 200)
    private String mensagem;

    @JsonProperty("moto_id")
    @NotNull(message = "O id da moto é obrigatório")
    private Long idMoto;
}
