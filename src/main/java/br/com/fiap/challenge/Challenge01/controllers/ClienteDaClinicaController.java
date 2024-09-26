package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.cliente.DadosAtualizarCliente;
import br.com.fiap.challenge.Challenge01.dto.cliente.DadosCriarCliente;
import br.com.fiap.challenge.Challenge01.dto.cliente.DadosListagemCliente;
import br.com.fiap.challenge.Challenge01.services.ClienteDaClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteDaClinicaController {
    @Autowired
    private ClienteDaClinicaService clienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criarCliente(@Valid @RequestBody DadosCriarCliente dados) {
        return clienteService.criarCliente(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarInformacoesCliente(@Valid @RequestBody DadosAtualizarCliente dados) {
        return clienteService.atualizarInformacoesCliente(dados);
    }

    @GetMapping
    public Page<DadosListagemCliente> listarTodosClientes(Pageable paginacao) {
        return clienteService.listarTodosClientes(paginacao);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> listarClientePorCPF(@PathVariable String cpf) {
        return clienteService.listarClientePorCPF(cpf);
    }
}
