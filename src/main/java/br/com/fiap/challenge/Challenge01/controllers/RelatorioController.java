package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosCriarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosListagemRelatorio;
import br.com.fiap.challenge.Challenge01.services.RelatorioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createRelatorio(@RequestBody @Valid DadosCriarRelatorio dados) {
        return relatorioService.createRelatorio(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateRelatorio(@RequestBody @Valid DadosAtualizarRelatorio dados) {
        return relatorioService.updateRelatorio(dados);
    }

    @GetMapping
    public Page<DadosListagemRelatorio> getAllRelatorio(Pageable paginacao) {
        return relatorioService.getAllRelatorio(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRelatorio(@PathVariable Long id) {
        return relatorioService.getRelatorio(id);
    }

    @GetMapping("/clinica/{clinica_id}")
    public Page<DadosListagemRelatorio> getRelatorioByClinica(@PathVariable Long clinica_id, Pageable paginacao) {
        return relatorioService.getRelatorioByClinica(clinica_id, paginacao);
    }

    @GetMapping("/cliente/{cliente_id}")
    public Page<DadosListagemRelatorio> getRelatorioByCliente(@PathVariable Long cliente_id, Pageable paginacao) {
        return relatorioService.getRelatorioByCliente(cliente_id, paginacao);
    }

    @DeleteMapping("/negar/{id}")
    @Transactional
    public ResponseEntity<?> recusarRelatorio(@PathVariable Long id){
        return relatorioService.recusarRelatorio(id);
    }

    @DeleteMapping("/aprovar/{id}")
    @Transactional
    public ResponseEntity<?> aprovarRelatorio(@PathVariable Long id){
        return relatorioService.aprovarRelatorio(id);
    }
}
