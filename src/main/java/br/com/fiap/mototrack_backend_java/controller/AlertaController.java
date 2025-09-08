package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.AlertaRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Alerta;
import br.com.fiap.mototrack_backend_java.service.AlertaService;
import br.com.fiap.mototrack_backend_java.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    @Autowired
    private MotoService motoService;

    @GetMapping("/moto/{id}")
    public String listarPorMoto(@PathVariable("id") Long idMoto, Model model) {
        var alertas = alertaService.buscarAlertasPorIdDaMoto(idMoto);
        var moto = motoService.buscarPorId(idMoto);

        model.addAttribute("alertas", alertas);
        model.addAttribute("moto", moto);
        return "alertas";
    }

    @PostMapping("/cadastrar")
    public String cadastrarAlerta(@ModelAttribute AlertaRequestDTO alerta) {
        Alerta alertaSalvo = alertaService.salvar(alerta);
        Long motoId = alertaSalvo.getMoto().getId();

        return "redirect:/alertas/moto/" + motoId;
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        var alerta = alertaService.buscarPorId(id);
        Long motoId = alerta.getMoto().getId();

        alertaService.deletar(id);

        return "redirect:/alertas/moto/" + motoId;
    }
}
