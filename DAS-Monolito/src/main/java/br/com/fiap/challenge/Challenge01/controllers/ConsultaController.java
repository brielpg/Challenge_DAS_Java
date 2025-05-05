package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.consulta.DtoAtualizarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoCriarConsulta;
import br.com.fiap.challenge.Challenge01.models.Consulta;
import br.com.fiap.challenge.Challenge01.services.ClinicaService;
import br.com.fiap.challenge.Challenge01.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/consulta")
public class ConsultaController {
    @Autowired
    private ConsultaService consultaService;
    @Autowired
    private ClinicaService clinicaService;

    @GetMapping("/create")
    public String createPage(Model model, Principal principal){
        var clinica = clinicaService.getClinicaByEmail(principal.getName());

        model.addAttribute("clinica", clinica);
        model.addAttribute("consulta", new Consulta());

        return "consulta/create";
    }

    @PostMapping
    @Transactional
    public String createConsulta(@Valid DtoCriarConsulta dados, Model model) {
        var consulta = consultaService.createConsulta(dados);
        model.addAttribute("consulta", consulta);
        return "redirect:consulta";
    }

    @GetMapping("/update/{id}")
    @Transactional
    public String updatePage(@PathVariable Long id, Model model) {
        var consulta = consultaService.findById(id);
        model.addAttribute("consulta", consulta);
        return "consulta/update";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateConsulta(@PathVariable Long id, @Valid DtoAtualizarConsulta dados, Model model) {
        var consulta = consultaService.updateConsulta(id, dados);
        model.addAttribute("consulta", consulta);
        return "redirect:/consulta";
    }

    @GetMapping
    public String getAllConsultas(Model model, Principal principal) {
        var consultas = consultaService.getAllConsultas(principal.getName());
        model.addAttribute("consultas", consultas);
        return "consulta/list";
    }

    @GetMapping("/{id}")
    public String getConsulta(@PathVariable Long id, Model model) {
        var consulta = consultaService.findById(id);
        model.addAttribute("consulta", consulta);
        return "consulta/detail";
    }
}
