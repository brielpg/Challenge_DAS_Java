package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.controllers.PacienteController;
import br.com.fiap.challenge.Challenge01.controllers.ClinicaController;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoListarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoRequestLogin;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicaService {
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Transactional
    public ResponseEntity<?> criarConta(DtoCriarClinica dados) {
        if (!clinicaRepository.existsByCnpj(dados.cnpj())){
            var clinica = clinicaRepository.save(new Clinica(dados));
            var retorno = new DtoListarClinica(clinica);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarTodasClinicas(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(clinica.getCnpj())).withSelfRel()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Já existe uma clínica registrada com esse CNPJ");
    }

    @Transactional
    public ResponseEntity<?> logarConta(DtoRequestLogin dados) {
        var clinica = clinicaRepository.findByCnpj(dados.cnpj());
        if (clinica != null && clinica.getSenha().equals(dados.senha())) {
            var retorno = new DtoListarClinica(clinica);
            retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(clinica.getCnpj())).withSelfRel());

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: CNPJ ou Senha incorretos.");
    }

    @Transactional
    public ResponseEntity<?> atualizarInformacoesConta(DtoAtualizarClinica dados) {
        if (clinicaRepository.existsById(dados.id())){
            var clinica = clinicaRepository.getReferenceById(dados.id());
            clinica.atualizarClinica(dados);

            var retorno = new DtoListarClinica(clinica);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(clinica.getCnpj())).withSelfRel()
            );

            for (var i : retorno.clientes) {
                i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarClientePorCPF(i.getCpf())).withRel("cliente"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Clínica não encontrado.");
    }

    @Transactional
    public Page<DtoListarClinica> listarTodasClinicas(Pageable paginacao) {
        var clinicas = clinicaRepository.findAll(paginacao).map(DtoListarClinica::new);

        clinicas.forEach(i -> i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(i.cnpj)).withSelfRel()));

        return clinicas;
    }

    @Transactional
    public ResponseEntity<?> listarClinicaPorCnpj(String cnpj) {
        var clinica = clinicaRepository.findByCnpj(cnpj);
        if (clinica != null) {
            var retorno = new DtoListarClinica(clinica);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarTodasClinicas(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
            );

            for (var i: retorno.clientes) {
                i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarClientePorCPF(i.getCpf())).withRel("cliente"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Clinica com este CNPJ não foi encontrada");
    }
}
