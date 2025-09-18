package br.com.fiap.mototrack_backend_java.config;

import br.com.fiap.mototrack_backend_java.service.UsuarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AdminInitializer {

    @Autowired
    private UsuarioService usuarioService;

    @PostConstruct
    public void init() {
        usuarioService.salvarAdminInicial();
    }
}
