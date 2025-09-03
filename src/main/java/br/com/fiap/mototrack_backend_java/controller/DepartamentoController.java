package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.DepartamentoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.DepartamentoResponseDTO;
import br.com.fiap.mototrack_backend_java.dto.MotoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MotoResponseDTO;
import br.com.fiap.mototrack_backend_java.mapper.DepartamentoMapper;
import br.com.fiap.mototrack_backend_java.model.Departamento;
import br.com.fiap.mototrack_backend_java.service.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<Page<DepartamentoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        var departamentos = departamentoService.listarTodos(paginacao);
        departamentos.forEach(this::adicionarLinks);

        return ResponseEntity.ok(departamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        var departamento = departamentoService.buscarPorId(id);
        adicionarLinks(departamento);

        return ResponseEntity.ok(departamento);
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponseDTO> salvar(@RequestBody @Valid DepartamentoRequestDTO departamentoRequestDTO, UriComponentsBuilder uriBuilder) {
        var departamento = departamentoService.salvar(departamentoRequestDTO);
        adicionarLinks(departamento);

        var uri = uriBuilder.path("/departamentos/{id}").buildAndExpand(departamento.getId()).toUri();
        return ResponseEntity.created(uri).body(departamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid DepartamentoRequestDTO departamentoRequestDTO) {
        var departamentoAtualizado = departamentoService.atualizar(id, departamentoRequestDTO);
        adicionarLinks(departamentoAtualizado);

        return ResponseEntity.ok(departamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        departamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private DepartamentoResponseDTO adicionarLinks(DepartamentoResponseDTO departamento) {
        departamento.add(linkTo(methodOn(DepartamentoController.class).buscarPorId(departamento.getId())).withRel("self"));
        departamento.add(linkTo(methodOn(DepartamentoController.class).atualizar(departamento.getId(), null)).withRel("update"));
        departamento.add(linkTo(methodOn(DepartamentoController.class).deletar(departamento.getId())).withRel("delete"));

        return departamento;
    }
}
