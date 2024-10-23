package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.consulta.DtoAtualizarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoCriarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoListarConsulta;
import br.com.fiap.challenge.Challenge01.services.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {
    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createConsulta(@RequestBody @Valid DtoCriarConsulta dados) {
        return consultaService.createConsulta(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateConsulta(@RequestBody @Valid DtoAtualizarConsulta dados) {
        return consultaService.updateConsulta(dados);
    }

    @GetMapping
    public Page<DtoListarConsulta> getAllConsultas(Pageable paginacao) {
        return consultaService.getAllConsultas(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConsulta(@PathVariable Long id) {
        return consultaService.getConsulta(id);
    }
}
