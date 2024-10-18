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
@RequestMapping("/cliente")
public class PacienteController {
    @Autowired
    private PacienteService clienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criarCliente(@Valid @RequestBody DtoCriarPaciente dados) {
        return clienteService.criarCliente(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarInformacoesCliente(@Valid @RequestBody DtoAtualizarPaciente dados) {
        return clienteService.atualizarInformacoesCliente(dados);
    }

    @GetMapping
    public Page<DtoListarPaciente> listarTodosClientes(Pageable paginacao) {
        return clienteService.listarTodosClientes(paginacao);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> listarClientePorCPF(@PathVariable String cpf) {
        return clienteService.listarClientePorCPF(cpf);
    }
}
