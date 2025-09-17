package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.UsuarioRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Usuario;
import br.com.fiap.mototrack_backend_java.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        if (error != null) {
            model.addAttribute("mensagemErro", "Email ou senha inválidos.");
        }

        return "login";
    }

    @GetMapping("/cadastrar")
    public String cadastroPagina(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioRequestDTO());
        return "cadastro";
    }

    @PostMapping("/cadastrar")
    public String cadastrarUsuario(@ModelAttribute("usuarioDTO") UsuarioRequestDTO usuarioDTO, Model model) {
        try {
            Usuario usuario = usuarioService.salvar(usuarioDTO, true);
            model.addAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Você será redirecionado para o login.");
            return "cadastro";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
            return "cadastro";
        }
    }
}
