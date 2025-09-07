package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.MovimentacaoRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Movimentacao;
import br.com.fiap.mototrack_backend_java.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Movimentacao buscarPorId(Long id) {
        return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimentação com id: " + id + " não encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Movimentacao> buscarMovimentacoesPorIdDaMoto(Long id) {
        var moto = motoService.buscarPorId(id);
        return movimentacaoRepository.findByMotoIdOrderByDataMovimentacaoAsc(moto.getId());
    }

    @Transactional
    public Movimentacao salvar(MovimentacaoRequestDTO dto) {
        var moto = motoService.buscarPorId(dto.getIdMoto());

        var departamento = departamentoService.buscarPorId(dto.getIdDepartamento());

        Movimentacao movimentacao = new Movimentacao();
        movimentacao.setMoto(moto);
        movimentacao.setDepartamento(departamento);
        return movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void deletar(Long id) {
        var movimentacao = buscarPorId(id);
        movimentacaoRepository.delete(movimentacao);
    }
}
