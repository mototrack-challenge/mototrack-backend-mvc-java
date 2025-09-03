package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.DepartamentoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.DepartamentoResponseDTO;
import br.com.fiap.mototrack_backend_java.mapper.DepartamentoMapper;
import br.com.fiap.mototrack_backend_java.model.Departamento;
import br.com.fiap.mototrack_backend_java.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public Page<DepartamentoResponseDTO> listarTodos(Pageable pageable) {
        return departamentoRepository.findAllByOrderByIdAsc(pageable)
                .map(DepartamentoMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public DepartamentoResponseDTO buscarPorId(Long id) {
        var departamento = buscarEntidadeDepartamentoPorId(id);
        return DepartamentoMapper.toResponseDTO(departamento);
    }

    @Transactional
    public DepartamentoResponseDTO salvar(DepartamentoRequestDTO departamentoRequestDTO) {
        var departamento = departamentoRepository.save(DepartamentoMapper.toEntity(departamentoRequestDTO));
        return DepartamentoMapper.toResponseDTO(departamento);
    }

    @Transactional
    public DepartamentoResponseDTO atualizar(Long id, DepartamentoRequestDTO departamentoRequestDTO) {
        var departamentoAtual = buscarEntidadeDepartamentoPorId(id);

        departamentoAtual.setId(id);
        departamentoAtual.setTipo(departamentoRequestDTO.getTipo());
        departamentoAtual.setDescricao(departamentoRequestDTO.getDescricao());

        var departamentoAtualizado = departamentoRepository.save(departamentoAtual);
        return DepartamentoMapper.toResponseDTO(departamentoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        var departamento = buscarEntidadeDepartamentoPorId(id);
        departamentoRepository.delete(departamento);
    }

    public Departamento buscarEntidadeDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Departamento com id: " + id + " não encontrado"));
    }
}
