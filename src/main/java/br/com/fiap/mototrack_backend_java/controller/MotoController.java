package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.MotoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MotoResponseDTO;
import br.com.fiap.mototrack_backend_java.model.Alerta;
import br.com.fiap.mototrack_backend_java.model.Movimentacao;
import br.com.fiap.mototrack_backend_java.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public String listarTodos(Model model) {
        var motos = motoService.listarTodos();

        // Para cada moto, já ordenamos e limitamos as movimentações para as últimas 5
        motos.forEach(moto -> {
            List<Movimentacao> ultimasMovimentacoes = moto.getMovimentacoes()
                    .stream()
                    .sorted(Comparator.comparing(Movimentacao::getDataMovimentacao).reversed())
                    .limit(5)
                    .toList();
            moto.setMovimentacoes(ultimasMovimentacoes); // substitui a lista original
        });

        motos.forEach(moto -> {
            List<Alerta> ultimosAlertas = moto.getAlertas()
                    .stream()
                    .sorted(Comparator.comparing(Alerta::getDataAlerta).reversed())
                    .limit(5)
                    .toList();
            moto.setAlertas(ultimosAlertas); // substitui a lista original
        });

        model.addAttribute("motos", motos);
        return "lista-motos";
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoResponseDTO> buscarPorId(@PathVariable Long id) {
        var moto = motoService.buscarPorId(id);

        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<MotoResponseDTO> salvar(@RequestBody @Valid MotoRequestDTO motoRequestDTO, UriComponentsBuilder uriBuilder) {
        var moto = motoService.salvar(motoRequestDTO);

        var uri = uriBuilder.path("/motos/{id}").buildAndExpand(moto.getId()).toUri();
        return ResponseEntity.created(uri).body(moto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MotoRequestDTO motoRequestDTO) {
        var motoAtualizada = motoService.atualizar(id, motoRequestDTO);

        return ResponseEntity.ok(motoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
