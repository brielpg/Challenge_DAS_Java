package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoListarRelatorio;
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

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateRelatorio(@RequestBody @Valid DtoAtualizarRelatorio dados) {
        return relatorioService.updateRelatorio(dados);
    }

    @GetMapping
    public Page<DtoListarRelatorio> getAllRelatorio(Pageable paginacao) {
        return relatorioService.getAllRelatorio(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRelatorio(@PathVariable Long id) {
        return relatorioService.getRelatorio(id);
    }

    @GetMapping("/clinica/{clinica_id}")
    public Page<DtoListarRelatorio> getRelatorioByClinica(@PathVariable Long clinica_id, Pageable paginacao) {
        return relatorioService.getRelatorioByClinica(clinica_id, paginacao);
    }

    @GetMapping("/paciente/{paciente_id}")
    public Page<DtoListarRelatorio> getRelatorioByPaciente(@PathVariable Long paciente_id, Pageable paginacao) {
        return relatorioService.getRelatorioByPaciente(paciente_id, paginacao);
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
