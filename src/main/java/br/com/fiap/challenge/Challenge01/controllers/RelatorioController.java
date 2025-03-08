package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.services.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable Long id, Model model){
        var relatorio = relatorioService.findById(id);
        model.addAttribute("relatorio", relatorio);
        return "relatorio/update";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateRelatorio(@PathVariable Long id, DtoAtualizarRelatorio dados, Model model) {
        var relatorio = relatorioService.updateRelatorio(id, dados);
        model.addAttribute("relatorio", relatorio);
        return "redirect:/relatorio";
    }

    @GetMapping
    public String getAllRelatorio(Model model) {
        var relatorios = relatorioService.getAllRelatorio();
        model.addAttribute("relatorios", relatorios);
        return "relatorio/list";
    }

    @GetMapping("/{id}")
    public String getRelatorioById(@PathVariable Long id, Model model) {
        var relatorio = relatorioService.getRelatorioById(id);
        model.addAttribute("relatorio", relatorio);
        return "relatorio/detail";
    }

    @GetMapping("/negar/{id}")
    @Transactional
    public String recusarRelatorio(@PathVariable Long id){
        relatorioService.recusarRelatorio(id);
        return "redirect:/relatorio";
    }

    @GetMapping("/aprovar/{id}")
    @Transactional
    public String aprovarRelatorio(@PathVariable Long id){
        relatorioService.aprovarRelatorio(id);
        return "redirect:/relatorio";
    }
}
