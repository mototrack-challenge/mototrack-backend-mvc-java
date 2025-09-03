package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.MotoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MotoResponseDTO;
import br.com.fiap.mototrack_backend_java.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<Page<MotoResponseDTO>> listarTodos(
            @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        var motos = motoService.listarTodos(paginacao);
        motos.forEach(this::adicionarLinks);

        return ResponseEntity.ok(motos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoResponseDTO> buscarPorId(@PathVariable Long id) {
        var moto = motoService.buscarPorId(id);
        adicionarLinks(moto);

        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<MotoResponseDTO> salvar(@RequestBody @Valid MotoRequestDTO motoRequestDTO, UriComponentsBuilder uriBuilder) {
        var moto = motoService.salvar(motoRequestDTO);
        adicionarLinks(moto);

        var uri = uriBuilder.path("/motos/{id}").buildAndExpand(moto.getId()).toUri();
        return ResponseEntity.created(uri).body(moto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MotoRequestDTO motoRequestDTO) {
        var motoAtualizada = motoService.atualizar(id, motoRequestDTO);
        adicionarLinks(motoAtualizada);

        return ResponseEntity.ok(motoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private MotoResponseDTO adicionarLinks(MotoResponseDTO moto) {
        moto.add(linkTo(methodOn(MotoController.class).buscarPorId(moto.getId())).withRel("self"));
        moto.add(linkTo(methodOn(MotoController.class).atualizar(moto.getId(), null)).withRel("update"));
        moto.add(linkTo(methodOn(MotoController.class).deletar(moto.getId())).withRel("delete"));

        return moto;
    }

}
