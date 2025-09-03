package br.com.fiap.mototrack_backend_java.mapper;

import br.com.fiap.mototrack_backend_java.dto.MotoResponseDTO;
import br.com.fiap.mototrack_backend_java.dto.MovimentacaoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MovimentacaoResponseDTO;
import br.com.fiap.mototrack_backend_java.model.Departamento;
import br.com.fiap.mototrack_backend_java.model.Moto;
import br.com.fiap.mototrack_backend_java.model.Movimentacao;

public class MovimentacaoMapper {

    public static MovimentacaoResponseDTO toResponseDTO(Movimentacao movimentacao) {
        if (movimentacao == null) return null;

        Long motoId = movimentacao.getMoto() != null ? movimentacao.getMoto().getId() : null;
        Departamento departamento = movimentacao.getDepartamento();

        Long departamentoId = departamento != null ? departamento.getId() : null;
        String departamentoDescricao = departamento != null ? departamento.getDescricao() : null;

        return new MovimentacaoResponseDTO(
                movimentacao.getId(),
                movimentacao.getDataMovimentacao(),
                motoId,
                departamentoId,
                departamentoDescricao

        );
    }

    public static Movimentacao toEntity(MovimentacaoRequestDTO dto, Moto moto, Departamento departamento) {
        if (dto == null) return null;

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setMoto(moto);
        movimentacao.setDepartamento(departamento);
        return movimentacao;
    }
}
