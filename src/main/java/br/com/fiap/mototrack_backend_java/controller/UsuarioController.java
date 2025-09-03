package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.UsuarioRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.UsuarioResponseDTO;
import br.com.fiap.mototrack_backend_java.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(
            @PageableDefault(size = 10, page = 0, sort = {"id"}) Pageable paginacao) {
        var usuarios = usuarioService.listarTodos(paginacao);
        usuarios.forEach(this::adicionarLinks);

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        adicionarLinks(usuario);

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> salvar(@RequestBody @Valid UsuarioRequestDTO usuarioDTO, UriComponentsBuilder uriBuilder) {
        var usuario = usuarioService.salvar(usuarioDTO);
        adicionarLinks(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO usuarioDTO) {
        var usuarioAtualizado = usuarioService.atualizar(id, usuarioDTO);
        adicionarLinks(usuarioAtualizado);

        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponseDTO adicionarLinks(UsuarioResponseDTO usuario) {
        usuario.add(linkTo(methodOn(UsuarioController.class).buscarPorId(usuario.getId())).withRel("self"));
        usuario.add(linkTo(methodOn(UsuarioController.class).atualizar(usuario.getId(), null)).withRel("update"));
        usuario.add(linkTo(methodOn(UsuarioController.class).deletar(usuario.getId())).withRel("delete"));

        return usuario;
    }
}
