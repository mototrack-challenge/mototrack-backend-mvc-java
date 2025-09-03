package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.*;
import br.com.fiap.mototrack_backend_java.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @GetMapping
    public ResponseEntity<Page<AlertaResponseDTO>> listarTodos(
            @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        var alertas = alertaService.listarTodos(paginacao);
        alertas.forEach(this::adicionarLinks);

        return ResponseEntity.ok(alertas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> buscarPorId(@PathVariable Long id) {
        var alerta = alertaService.buscarPorId(id);
        adicionarLinks(alerta);

        return ResponseEntity.ok(alerta);
    }

    @GetMapping("/moto/{id}")
    public ResponseEntity<List<AlertaResponseDTO>> listarPorMoto(@PathVariable Long id) {
        var alertas = alertaService.buscarAlertasPorIdDaMoto(id);
        return ResponseEntity.ok(alertas);
    }

    @PostMapping
    public ResponseEntity<AlertaResponseDTO> salvar(@RequestBody @Valid AlertaRequestDTO alertaRequestDTO, UriComponentsBuilder uriBuilder) {
        var alerta = alertaService.salvar(alertaRequestDTO);
        adicionarLinks(alerta);

        var uri = uriBuilder.path("/alertas/{id}").buildAndExpand(alerta.getId()).toUri();
        return ResponseEntity.created(uri).body(alerta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AlertaRequestDTO alertaRequestDTO) {
        var alertaAtualizado = alertaService.atualizar(id, alertaRequestDTO);
        adicionarLinks(alertaAtualizado);

        return ResponseEntity.ok(alertaAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private AlertaResponseDTO adicionarLinks(AlertaResponseDTO alerta) {
        alerta.add(linkTo(methodOn(AlertaController.class).buscarPorId(alerta.getId())).withRel("self"));
        alerta.add(linkTo(methodOn(AlertaController.class).atualizar(alerta.getId(), null)).withRel("update"));
        alerta.add(linkTo(methodOn(AlertaController.class).deletar(alerta.getId())).withRel("delete"));

        return alerta;
    }
}
