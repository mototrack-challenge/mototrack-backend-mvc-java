package br.com.fiap.mototrack_backend_java.mapper;

import br.com.fiap.mototrack_backend_java.dto.AlertaRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.AlertaResponseDTO;
import br.com.fiap.mototrack_backend_java.model.Alerta;
import br.com.fiap.mototrack_backend_java.model.Moto;

public class AlertaMapper {

    public static AlertaResponseDTO toResponseDTO(Alerta alerta) {
        if (alerta == null) return null;

        Long motoId = alerta.getMoto() != null ? alerta.getMoto().getId() : null;

        return new AlertaResponseDTO(
                alerta.getId(),
                alerta.getGravidade(),
                alerta.getMensagem(),
                alerta.getDataAlerta(),
                motoId
        );
    }

    public static Alerta toEntity(AlertaRequestDTO dto, Moto moto) {
        if (dto == null) return null;

        Alerta alerta = new Alerta();
        alerta.setGravidade(dto.getGravidade());
        alerta.setMensagem(dto.getMensagem());
        alerta.setMoto(moto);

        return alerta;
    }
}
