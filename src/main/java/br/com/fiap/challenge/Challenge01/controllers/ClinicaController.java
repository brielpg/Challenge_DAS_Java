package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.clinica.DadosAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosListagemClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosRequestLogin;
import br.com.fiap.challenge.Challenge01.services.ClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinica")
public class ClinicaController {
    @Autowired
    private ClinicaService clinicaService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criarConta(@RequestBody @Valid DadosCriarClinica dados) {
        return clinicaService.criarConta(dados);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> logarConta(@RequestBody @Valid DadosRequestLogin dados) {
        return clinicaService.logarConta(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarInformacoesConta(@RequestBody @Valid DadosAtualizarClinica dados) {
        return clinicaService.atualizarInformacoesConta(dados);
    }

    @GetMapping
    public Page<DadosListagemClinica> listarTodasClinicas(Pageable paginacao) {
        return clinicaService.listarTodasClinicas(paginacao);
    }
}
