package br.com.fiap.mototrack_backend_java.service;

import br.com.fiap.mototrack_backend_java.dto.UsuarioRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Usuario;
import br.com.fiap.mototrack_backend_java.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(UsuarioRequestDTO usuarioDTO) {
        return usuarioRepository.save(usuarioDTO.toEntity(passwordEncoder));
    }
}
