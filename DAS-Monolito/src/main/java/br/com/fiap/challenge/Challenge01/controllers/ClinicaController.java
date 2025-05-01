package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.enums.DasRoles;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.services.ClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clinica")
public class ClinicaController {
    @Autowired
    private ClinicaService clinicaService;

    @GetMapping("/create")
    public String createPage(Model model){

        model.addAttribute("clinica", new Clinica());

        return "clinica/create";
    }

    @PostMapping
    @Transactional
    public String createClinica(@Valid DtoCriarClinica dados, Model model) {
        var clinica = clinicaService.createClinica(dados);
        model.addAttribute("clinica", clinica);
        return "redirect:clinica";
    }

    @GetMapping("/update/{id}")
    @Transactional
    public String updatePage(@PathVariable Long id, Model model) {
        var clinica = clinicaService.findById(id);
        model.addAttribute("clinica", clinica);
        return "clinica/update";
    }

    @PostMapping("/update/{id}")
    @Transactional
    public String updateClinica(@PathVariable Long id, @Valid DtoAtualizarClinica dados, Model model) {
        var clinica = clinicaService.updateClinica(id, dados);
        model.addAttribute("clinica", clinica);
        return "redirect:/clinica";
    }

    @PostMapping("/changeRole")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public String changeRole(@RequestParam Long id, @RequestParam String role) {
        clinicaService.changeRole(id, role);
        return "redirect:/clinica";
    }

    @GetMapping
    public String getAllClinicas(Model model) {
        var clinicas = clinicaService.getAllClinicas();
        model.addAttribute("clinicas", clinicas);
        return "clinica/list";
    }

    @GetMapping("/{cnpj}")
    public String getClinicaByCnpj(@PathVariable String cnpj, Model model) {
        var clinica = clinicaService.getClinicaByCnpj(cnpj);
        model.addAttribute("clinica", clinica);
        return "clinica/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        clinicaService.deleteById(id);
        return "redirect:/clinica";
    }
}
