package br.com.fiap.mototrack_backend_java.dto;

import br.com.fiap.mototrack_backend_java.model.enums.ModeloMoto;
import br.com.fiap.mototrack_backend_java.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoRequestDTO {

    @NotBlank(message = "A placa da moto é obrigatória")
    @Size(min = 7, max = 7)
    private String placa;

    @NotBlank(message = "O número do chassi é obrigatório")
    @Size(min = 17, max = 17)
    private String chassi;

    @NotNull(message = "O modelo da moto é obrigatório")
    private ModeloMoto modelo;

    @NotNull(message = "O status é obrigatório")
    private Status status;
}
