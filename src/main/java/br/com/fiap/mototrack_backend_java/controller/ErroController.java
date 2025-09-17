package br.com.fiap.mototrack_backend_java.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErroController {

    @GetMapping("/403")
    public String acessoNegado() {
        return "error/403";
    }
}
