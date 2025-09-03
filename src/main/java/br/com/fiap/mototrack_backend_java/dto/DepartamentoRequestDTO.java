package br.com.fiap.mototrack_backend_java.dto;

import br.com.fiap.mototrack_backend_java.model.enums.TipoDepartamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoRequestDTO {

    @NotNull(message = "O tipo do departamento é obrigatório")
    @JsonProperty("tipo_departamento")
    private TipoDepartamento tipo;

    @NotBlank(message = "A descrição do departamento é obrigatório")
    private String descricao;

}
