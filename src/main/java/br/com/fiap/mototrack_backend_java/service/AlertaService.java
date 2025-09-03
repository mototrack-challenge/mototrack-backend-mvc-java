package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.AlertaRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.AlertaResponseDTO;
import br.com.fiap.mototrack_backend_java.mapper.AlertaMapper;
import br.com.fiap.mototrack_backend_java.model.Alerta;
import br.com.fiap.mototrack_backend_java.repository.AlertaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertaService {

    @Autowired
    private AlertaRepository alertaRepository;

    @Autowired
    private MotoService motoService;

    @Transactional(readOnly = true)
    public Page<AlertaResponseDTO> listarTodos(Pageable pageable) {
        return alertaRepository.findAllByOrderByIdAsc(pageable)
                .map(AlertaMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public AlertaResponseDTO buscarPorId(Long id) {
        var alerta = buscarEntidadeAlertaPorId(id);
        return AlertaMapper.toResponseDTO(alerta);
    }

    @Transactional(readOnly = true)
    public List<AlertaResponseDTO> buscarAlertasPorIdDaMoto(Long id) {
        var moto = motoService.buscarEntidadeMotoPorId(id);
        var alertas = alertaRepository.findByMotoIdOrderByDataAlertaAsc(moto.getId());
        return alertas.stream()
                .map(AlertaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public AlertaResponseDTO salvar(AlertaRequestDTO alertaRequestDTO) {
        var moto = motoService.buscarEntidadeMotoPorId(alertaRequestDTO.getIdMoto());
        var alerta = alertaRepository.save(AlertaMapper.toEntity(alertaRequestDTO, moto));
        return AlertaMapper.toResponseDTO(alerta);
    }

    @Transactional
    public AlertaResponseDTO atualizar(Long id, AlertaRequestDTO alertaRequestDTO) {
        var alertaAtual = buscarEntidadeAlertaPorId(id);
        var moto = motoService.buscarEntidadeMotoPorId(alertaRequestDTO.getIdMoto());

        alertaAtual.setId(id);
        alertaAtual.setGravidade(alertaRequestDTO.getGravidade());
        alertaAtual.setMensagem(alertaRequestDTO.getMensagem());
        alertaAtual.setMoto(moto);

        var alertaAtualizado = alertaRepository.save(alertaAtual);
        return AlertaMapper.toResponseDTO(alertaAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        var alerta = buscarEntidadeAlertaPorId(id);
        alertaRepository.delete(alerta);
    }

    private Alerta buscarEntidadeAlertaPorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alerta com id: " + id + " não encontrado"));
    }
}
