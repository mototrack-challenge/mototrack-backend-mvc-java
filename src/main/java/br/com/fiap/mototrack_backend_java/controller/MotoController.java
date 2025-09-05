package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.MotoRequestDTO;
import br.com.fiap.mototrack_backend_java.dto.MotoResponseDTO;
import br.com.fiap.mototrack_backend_java.model.Alerta;
import br.com.fiap.mototrack_backend_java.model.Moto;
import br.com.fiap.mototrack_backend_java.model.Movimentacao;
import br.com.fiap.mototrack_backend_java.model.enums.ModeloMoto;
import br.com.fiap.mototrack_backend_java.model.enums.Status;
import br.com.fiap.mototrack_backend_java.model.enums.TipoDepartamento;
import br.com.fiap.mototrack_backend_java.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Comparator;
import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<MotoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid MotoRequestDTO motoRequestDTO) {
        var motoAtualizada = motoService.atualizar(id, motoRequestDTO);

        return ResponseEntity.ok(motoAtualizada);
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        motoService.deletar(id);
        return "redirect:/motos";
    }


}
