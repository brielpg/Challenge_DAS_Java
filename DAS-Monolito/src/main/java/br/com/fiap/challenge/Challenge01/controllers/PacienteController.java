package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import br.com.fiap.challenge.Challenge01.models.Paciente;
import br.com.fiap.challenge.Challenge01.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/create")
    public String createPage(Model model){

        model.addAttribute("paciente", new Paciente());

        return "paciente/create";
    }

    @PostMapping
    @Transactional
    public String createPaciente(@Valid DtoCriarPaciente dados, Model model) {
        var paciente = pacienteService.createPaciente(dados);
        model.addAttribute("paciente", paciente);
        return "redirect:paciente";
    }

    @GetMapping("/update/{cpf}")
    @Transactional
    public String updatePage(@PathVariable String cpf, Model model) {
        var paciente = pacienteService.getPacienteByCpf(cpf);
        model.addAttribute("paciente", paciente);
        return "paciente/update";
    }

    @PostMapping("/update/{cpf}")
    @Transactional
    public String updatePaciente(@PathVariable String cpf, DtoAtualizarPaciente dados, Model model) {
        var paciente = pacienteService.updatePaciente(cpf, dados);
        model.addAttribute("paciente", paciente);
        return "redirect:/paciente";
    }

    @GetMapping
    public String getAllPacientes(Model model) {
        var pacientes = pacienteService.getAllPacientes();
        model.addAttribute("pacientes", pacientes);
        return "paciente/list";
    }

    @GetMapping("/{cpf}")
    public String getPacienteByCpf(@PathVariable String cpf, Model model) {
        var paciente = pacienteService.getPacienteByCpf(cpf);
        model.addAttribute("paciente", paciente);
        return "paciente/detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        pacienteService.deleteById(id);
        return "redirect:/paciente";
    }
}
