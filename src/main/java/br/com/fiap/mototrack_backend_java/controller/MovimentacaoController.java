package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.MovimentacaoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MovimentacaoResponseDTO;
import br.com.fiap.mototrack_backend_java.service.MovimentacaoService;
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
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<Page<MovimentacaoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        var movimentacoes = movimentacaoService.listarTodos(paginacao);
        movimentacoes.forEach(this::adicionarLinks);

        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> buscarPorId(@PathVariable Long id) {
        var movimentacao = movimentacaoService.buscarPorId(id);
        adicionarLinks(movimentacao);

        return ResponseEntity.ok(movimentacao);
    }

    @GetMapping("/moto/{id}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorMoto(@PathVariable Long id) {
        var movimentacoes = movimentacaoService.buscarMovimentacoesPorIdDaMoto(id);
        return ResponseEntity.ok(movimentacoes);
    }

    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> salvar(@RequestBody @Valid MovimentacaoRequestDTO movimentacaoRequestDTO, UriComponentsBuilder uriBuilder) {
        var movimentacao = movimentacaoService.salvar(movimentacaoRequestDTO);
        adicionarLinks(movimentacao);

        var uri = uriBuilder.path("/movimentacoes/{id}").buildAndExpand(movimentacao.getId()).toUri();
        return ResponseEntity.created(uri).body(movimentacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MovimentacaoRequestDTO movimentacaoRequestDTO) {
        var movimentacaoAtualizada = movimentacaoService.atualizar(id, movimentacaoRequestDTO);
        adicionarLinks(movimentacaoAtualizada);

        return ResponseEntity.ok(movimentacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        movimentacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private MovimentacaoResponseDTO adicionarLinks(MovimentacaoResponseDTO movimentacao) {
        movimentacao.add(linkTo(methodOn(MovimentacaoController.class).buscarPorId(movimentacao.getId())).withRel("self"));
        movimentacao.add(linkTo(methodOn(MovimentacaoController.class).atualizar(movimentacao.getId(), null)).withRel("update"));
        movimentacao.add(linkTo(methodOn(MovimentacaoController.class).deletar(movimentacao.getId())).withRel("delete"));

        return movimentacao;
    }
}
