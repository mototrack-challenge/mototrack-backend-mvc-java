package br.com.fiap.mototrack_backend_java.controller;

import br.com.fiap.mototrack_backend_java.dto.MovimentacaoRequestDTO;
import br.com.fiap.mototrack_backend_java.model.Movimentacao;
import br.com.fiap.mototrack_backend_java.service.DepartamentoService;
import br.com.fiap.mototrack_backend_java.service.MotoService;
import br.com.fiap.mototrack_backend_java.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private MotoService motoService;

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping("/moto/{id}")
    public String listarPorMoto(@PathVariable("id") Long idMoto, Model model) {
        var movimentacoes = movimentacaoService.buscarMovimentacoesPorIdDaMoto(idMoto);
        var moto = motoService.buscarPorId(idMoto);

        model.addAttribute("movimentacoes", movimentacoes);
        model.addAttribute("moto", moto);
        model.addAttribute("departamentos", departamentoService.buscarTodos());

        return "movimentacoes";
    }

    @PostMapping("/cadastrar")
    public String cadastrarMovimentacao(@ModelAttribute MovimentacaoRequestDTO movimentacao) {
        Movimentacao movimentacaoSalva = movimentacaoService.salvar(movimentacao);
        Long motoId = movimentacaoSalva.getMoto().getId();

        return "redirect:/movimentacoes/moto/" + motoId;
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        var movimentacao = movimentacaoService.buscarPorId(id);
        Long motoId = movimentacao.getMoto().getId();

        movimentacaoService.deletar(id);

        return "redirect:/movimentacoes/moto/" + motoId;
    }
}
