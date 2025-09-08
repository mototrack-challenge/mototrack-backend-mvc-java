package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.AlertaRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Alerta;
import br.com.fiap.mototrack_backend_java.repository.AlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Alerta buscarPorId(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta com id: " + id + " não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Alerta> buscarAlertasPorIdDaMoto(Long id) {
        var moto = motoService.buscarPorId(id);
        return alertaRepository.findByMotoIdOrderByDataAlertaAsc(moto.getId());
    }

    @Transactional
    public Alerta salvar(AlertaRequestDTO dto) {
        var moto = motoService.buscarPorId(dto.getIdMoto());
        Alerta alerta = new Alerta();
        alerta.setMoto(moto);
        alerta.setGravidade(dto.getGravidade());
        alerta.setMensagem(dto.getMensagem());
        return alertaRepository.save(alerta);
    }

    @Transactional
    public void deletar(Long id) {
        var alerta = buscarPorId(id);
        alertaRepository.delete(alerta);
    }
}
