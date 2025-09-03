package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.MotoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MotoResponseDTO;
import br.com.fiap.mototrack_backend_java.mapper.MotoMapper;
import br.com.fiap.mototrack_backend_java.model.Moto;
import br.com.fiap.mototrack_backend_java.repository.MotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Transactional(readOnly = true)
    public Page<MotoResponseDTO> listarTodos(Pageable pageable) {
        return motoRepository.findAllByOrderByIdAsc(pageable)
                .map(MotoMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public MotoResponseDTO buscarPorId(Long id) {
        var moto = buscarEntidadeMotoPorId(id);
        return MotoMapper.toResponseDTO(moto);
    }

    @Transactional
    public MotoResponseDTO salvar(MotoRequestDTO motoRequestDTO) {
        var moto = motoRepository.save(MotoMapper.toEntity(motoRequestDTO));
        return MotoMapper.toResponseDTO(moto);
    }

    @Transactional
    public MotoResponseDTO atualizar(Long id, MotoRequestDTO motoRequestDTO) {
        var motoAtual = buscarEntidadeMotoPorId(id);

        motoAtual.setId(id);
        motoAtual.setPlaca(motoRequestDTO.getPlaca());
        motoAtual.setChassi(motoRequestDTO.getChassi());
        motoAtual.setModelo(motoRequestDTO.getModelo());
        motoAtual.setStatus(motoRequestDTO.getStatus());

        var motoAtualizada = motoRepository.save(motoAtual);
        return MotoMapper.toResponseDTO(motoAtualizada);
    }

    @Transactional
    public void deletar(Long id) {
        var moto = buscarEntidadeMotoPorId(id);
        motoRepository.delete(moto);
    }

    public Moto buscarEntidadeMotoPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moto com id: " + id + " não encontrada"));
    }
}
