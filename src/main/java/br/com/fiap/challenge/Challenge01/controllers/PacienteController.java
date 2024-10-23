package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoListarPaciente;
import br.com.fiap.challenge.Challenge01.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createPaciente(@Valid @RequestBody DtoCriarPaciente dados) {
        return pacienteService.createPaciente(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updatePaciente(@Valid @RequestBody DtoAtualizarPaciente dados) {
        return pacienteService.updatePaciente(dados);
    }

    @GetMapping
    public Page<DtoListarPaciente> getAllPacientes(Pageable paginacao) {
        return pacienteService.getAllPacientes(paginacao);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> getPacienteByCpf(@PathVariable String cpf) {
        return pacienteService.getPacienteByCpf(cpf);
    }
}
