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
    public ResponseEntity<?> criarRelatorio(@RequestBody @Valid DadosCriarRelatorio dados) {
        return relatorioService.criarRelatorio(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualizarInformacoesRelatorio(@RequestBody @Valid DadosAtualizarRelatorio dados) {
        return relatorioService.atualizarInformacoesRelatorio(dados);
    }

    @GetMapping
    public Page<DadosListagemRelatorio> listarTodosRelatorios(Pageable paginacao) {
        return relatorioService.listarTodosRelatorios(paginacao);
    }

    @GetMapping("/clinica/{clinicaId}")
    public Page<DadosListagemRelatorio> listarRelatoriosPorClinica(@PathVariable Long id_clinica, Pageable paginacao) {
        return relatorioService.listarRelatoriosPorClinica(id_clinica, paginacao);
    }

    @GetMapping("/cliente/{clienteId}")
    public Page<DadosListagemRelatorio> listarRelatoriosPorCliente(@PathVariable Long id_cliente, Pageable paginacao) {
        return relatorioService.listarRelatoriosPorCliente(id_cliente, paginacao);
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
