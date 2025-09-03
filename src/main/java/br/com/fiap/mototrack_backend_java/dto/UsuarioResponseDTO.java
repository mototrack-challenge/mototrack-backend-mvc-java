package br.com.fiap.mototrack_backend_java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id_usuario", "nome", "email", "senha", "data_criacao", "_links"})
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {

    @JsonProperty("id_usuario")
    private Long id;

    private String nome;

    private String email;

    private String senha;

    @JsonProperty("data_criacao")
    private LocalDateTime dataCriacao;
}
