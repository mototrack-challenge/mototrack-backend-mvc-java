package br.com.fiap.mototrack_backend_java.mapper;

import br.com.fiap.mototrack_backend_java.dto.DepartamentoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.DepartamentoResponseDTO;
import br.com.fiap.mototrack_backend_java.model.Departamento;

public class DepartamentoMapper {

    public static DepartamentoResponseDTO toResponseDTO(Departamento departamento){
        if (departamento == null) return null;

        return new DepartamentoResponseDTO(
                departamento.getId(),
                departamento.getTipo(),
                departamento.getDescricao()

        );
    }

    public static Departamento toEntity(DepartamentoRequestDTO dto) {
        if (dto == null) return null;

        Departamento departamento = new Departamento();
        departamento.setDescricao(dto.getDescricao());
        departamento.setTipo(dto.getTipo());

        return departamento;
    }
}
