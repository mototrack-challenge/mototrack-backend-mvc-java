package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.MovimentacaoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MovimentacaoResponseDTO;
import br.com.fiap.mototrack_backend_java.mapper.MovimentacaoMapper;
import br.com.fiap.mototrack_backend_java.model.Movimentacao;
import br.com.fiap.mototrack_backend_java.repository.MovimentacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovimentacaoService  {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private MotoService motoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Transactional(readOnly = true)
    public Page<MovimentacaoResponseDTO> listarTodos(Pageable pageable) {
        return movimentacaoRepository.findAllByOrderByIdAsc(pageable)
                .map(MovimentacaoMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public MovimentacaoResponseDTO buscarPorId(Long id) {
        var movimentacao = buscarEntidadeMovimentacaoPorId(id);
        return MovimentacaoMapper.toResponseDTO(movimentacao);
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoResponseDTO> buscarMovimentacoesPorIdDaMoto(Long id) {
        var moto = motoService.buscarEntidadeMotoPorId(id);
        var movimentacoes = movimentacaoRepository.findByMotoIdOrderByDataMovimentacaoAsc(moto.getId());
        return movimentacoes.stream()
                .map(MovimentacaoMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public MovimentacaoResponseDTO salvar(MovimentacaoRequestDTO movimentacaoRequestDTO) {
        var moto = motoService.buscarEntidadeMotoPorId(movimentacaoRequestDTO.getIdMoto());
        var departamento = departamentoService.buscarEntidadeDepartamentoPorId(movimentacaoRequestDTO.getIdDepartamento());
        var movimentacao = movimentacaoRepository.save(MovimentacaoMapper.toEntity(movimentacaoRequestDTO, moto, departamento));
        return MovimentacaoMapper.toResponseDTO(movimentacao);
    }

    @Transactional
    public MovimentacaoResponseDTO atualizar(Long id, MovimentacaoRequestDTO movimentacaoRequestDTO) {
        var movimentacaoAtual = buscarEntidadeMovimentacaoPorId(id);
        var moto = motoService.buscarEntidadeMotoPorId(movimentacaoRequestDTO.getIdMoto());
        var departamento = departamentoService.buscarEntidadeDepartamentoPorId(movimentacaoRequestDTO.getIdDepartamento());

        movimentacaoAtual.setId(id);
        movimentacaoAtual.setMoto(moto);
        movimentacaoAtual.setDepartamento(departamento);

        var movimentacaoAtualizada = movimentacaoRepository.save(movimentacaoAtual);
        return MovimentacaoMapper.toResponseDTO(movimentacaoAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        var movimentacao = buscarEntidadeMovimentacaoPorId(id);
        movimentacaoRepository.delete(movimentacao);
    }

    private Movimentacao buscarEntidadeMovimentacaoPorId(Long id) {
        return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimentação com id: " + id + " não encontrada"));
    }
}
