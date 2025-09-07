package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.model.Moto;
import br.com.fiap.mototrack_backend_java.model.enums.ModeloMoto;
import br.com.fiap.mototrack_backend_java.model.enums.Status;
import br.com.fiap.mototrack_backend_java.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public String listarTodos(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) ModeloMoto modelo,
            @RequestParam(required = false) Status status,
            Model model) {

        var motos = motoService.listarMotos(placa, chassi, modelo, status);
        var resumo = motoService.resumirCards();

        if (motos.isEmpty()) {
            if (modelo == null && status == null && (placa == null || placa.isBlank()) && (chassi == null || chassi.isBlank())) {
                model.addAttribute("mensagemVazio", true);
            } else {
                model.addAttribute("mensagemFiltro", true);
            }
        }

        model.addAttribute("motos", motos);
        model.addAttribute("resumo", resumo);

        return "lista-motos";
    }

    @GetMapping("/cadastrar")
    public String cadastrarMotoForm(Model model) {
        model.addAttribute("moto", new Moto());
        return "cadastro-moto";
    }

    @PostMapping("/cadastrar")
    public String cadastrarMoto(@ModelAttribute Moto moto, Model model) {
        boolean temErro = false;
        model.addAttribute("erroPlaca", null);
        model.addAttribute("erroChassi", null);

        if (motoService.existePorPlaca(moto.getPlaca())) {
            model.addAttribute("erroPlaca", "Já existe uma moto cadastrada com essa placa.");
            temErro = true;
        }

        if (motoService.existePorChassi(moto.getChassi())) {
            model.addAttribute("erroChassi", "Já existe uma moto cadastrada com esse chassi.");
            temErro = true;
        }

        if (temErro) {
            model.addAttribute("moto", moto);
            return "cadastro-moto";
        }

        motoService.salvar(moto);
        return "redirect:/motos";
    }

    @GetMapping("/editar/{id}")
    public String editarMotoForm(@PathVariable Long id, Model model) {
        Moto moto = motoService.buscarPorId(id);
        model.addAttribute("moto", moto);
        return "editar-moto";
    }

    @PostMapping("/editar/{id}")
    public String editarMoto(@PathVariable Long id, @ModelAttribute Moto moto, Model model) {
        boolean temErro = false;
        model.addAttribute("erroPlaca", null);
        model.addAttribute("erroChassi", null);

        Moto motoOriginal = motoService.buscarPorId(id);

        if (!moto.getPlaca().equalsIgnoreCase(motoOriginal.getPlaca())
                && motoService.existePorPlaca(moto.getPlaca())) {
            model.addAttribute("erroPlaca", "Já existe uma moto cadastrada com essa placa.");
            temErro = true;
        }

        if (!moto.getChassi().equalsIgnoreCase(motoOriginal.getChassi())
                && motoService.existePorChassi(moto.getChassi())) {
            model.addAttribute("erroChassi", "Já existe uma moto cadastrada com esse chassi.");
            temErro = true;
        }

        if (temErro) {
            model.addAttribute("moto", moto);
            return "editar-moto";
        }

        motoService.atualizar(id, moto);
        return "redirect:/motos";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return "redirect:/motos";
    }

}
