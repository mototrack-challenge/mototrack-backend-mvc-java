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

    @GetMapping("/usuarios")
    public String listarTodos(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email
            , Model model) {

        var usuarios = usuarioService.listarUsuarios(nome, email);

        if (usuarios.isEmpty()) {
            if ((nome == null || nome.isBlank()) && (email == null || email.isBlank())) {
                model.addAttribute("mensagemVazio", true);
            } else {
                model.addAttribute("mensagemFiltro", true);
            }
        }

        model.addAttribute("usuarios", usuarios);

        return "lista-usuarios";
    }

    @GetMapping("/usuarios/cadastrar")
    public String cadastroUsuarioPaginaAdmin(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioRequestDTO());
        return "cadastro-usuario";
    }

    @PostMapping("/usuarios/cadastrar")
    public String cadastrarUsuarioAdmin(@ModelAttribute("usuarioDTO") UsuarioRequestDTO usuarioDTO, Model model) {
        try {
            Usuario usuario = usuarioService.salvar(usuarioDTO, false);
            model.addAttribute("mensagemSucesso", "Cadastro de usuário realizado com sucesso!");
            return "cadastro-usuario";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
            return "cadastro-usuario";
        }
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuarioPaginaAdmin(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "editar-usuario";
    }

    @PostMapping("usuarios/editar/{id}")
    public String editarUsuarioAdmin(@PathVariable Long id, @ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.atualizar(id, usuario);
            model.addAttribute("mensagemSucesso", "Edição de usuário realizado com sucesso!");
            return "editar-usuario";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", e.getMessage());
            return "editar-usuario";
        }
    }

    @GetMapping("usuarios/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return "redirect:/usuarios";
    }
}
