package br.com.fiap.challenge.Challenge01.controllers;

import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoListarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoRequestLogin;
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
    public ResponseEntity<?> createClinica(@RequestBody @Valid DtoCriarClinica dados) {
        return clinicaService.createClinica(dados);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<?> authClinica(@RequestBody @Valid DtoRequestLogin dados) {
        return clinicaService.authClinica(dados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updateClinica(@RequestBody @Valid DtoAtualizarClinica dados) {
        return clinicaService.updateClinica(dados);
    }

    @GetMapping
    public Page<DtoListarClinica> getAllClinicas(Pageable paginacao) {
        return clinicaService.getAllClinicas(paginacao);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<?> getClinicaByCnpj(@PathVariable String cnpj) {
        return clinicaService.getClinicaByCnpj(cnpj);
    }
}
