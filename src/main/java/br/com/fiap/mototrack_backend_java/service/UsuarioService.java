package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.UsuarioRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Usuario;
import br.com.fiap.mototrack_backend_java.model.enums.Perfil;
import br.com.fiap.mototrack_backend_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(String nome, String email) {
        return usuarioRepository.findByFiltros(nome, email);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário com id: " + id + " não encontrado"));
    }

    @Transactional
    public Usuario salvar(UsuarioRequestDTO usuarioDTO, boolean cadastroPublico) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Este email já está cadastrado.");
        }

        Usuario usuario = usuarioDTO.toEntity(passwordEncoder);
        if (cadastroPublico || usuario.getPerfil() == null) {
            usuario.setPerfil(Perfil.COMUM);
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioNovo) {
        var usuarioAtual = buscarPorId(id);

        usuarioAtual.setId(id);
        usuarioAtual.setNome(usuarioNovo.getNome());
        if (!usuarioAtual.getEmail().equals(usuarioNovo.getEmail())) {
            if (usuarioRepository.findByEmail(usuarioNovo.getEmail()).isPresent()) {
                throw new RuntimeException("Este email já está cadastrado.");
            }
            usuarioAtual.setEmail(usuarioNovo.getEmail());
        }

        if (usuarioNovo.getSenha() != null && !usuarioNovo.getSenha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(usuarioNovo.getSenha());
            usuarioAtual.setSenha(senhaCriptografada);
        }

        usuarioAtual.setPerfil(usuarioNovo.getPerfil());

        return usuarioRepository.save(usuarioAtual);
    }

    @Transactional
    public void deletar(Long id) {
        var usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}
