package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.controllers.PacienteController;
import br.com.fiap.challenge.Challenge01.controllers.ClinicaController;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoListarPaciente;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.models.Paciente;
import br.com.fiap.challenge.Challenge01.repositories.EnderecoRepository;
import br.com.fiap.challenge.Challenge01.repositories.PacienteRepository;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public ResponseEntity<?> createPaciente(@Valid @RequestBody DtoCriarPaciente dados) {
        if (!pacienteRepository.existsByCpf(dados.cpf())){
            var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
            var paciente = new Paciente(dados);
            paciente.setEndereco(endereco);
            pacienteRepository.save(paciente);

            var retorno = new DtoListarPaciente(paciente);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getAllPacientes(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(paciente.getCpf())).withSelfRel()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: já existe um paciente cadastrado com esse CPF");
    }

    @Transactional
    public Page<DtoListarPaciente> getAllPacientes(Pageable paginacao) {
        var pacientes = pacienteRepository.findAll(paginacao).map(DtoListarPaciente::new);

        pacientes.forEach(i -> i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(i.cpf)).withSelfRel()));

        return pacientes;
    }

    @Transactional
    public ResponseEntity<?> getPacienteByCpf(String cpf) {
        var paciente = pacienteRepository.findByCpf(cpf);
        if (paciente != null) {
            var retorno = new DtoListarPaciente(paciente);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getAllPacientes(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
            );

            for (var i: retorno.clinicas) {
                i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).getClinicaByCnpj(i.getCnpj())).withRel("clinica"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Paciente com este CPF não encontrado");
    }

    @Transactional
    public ResponseEntity<?> updatePaciente(DtoAtualizarPaciente dados) {
        if (pacienteRepository.existsByCpf(dados.cpf())){
            var paciente = pacienteRepository.getReferenceByCpf(dados.cpf());
            paciente.atualizarPaciente(dados);

            var retorno = new DtoListarPaciente(paciente);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(paciente.getCpf())).withSelfRel()
            );

            for (var i : retorno.clinicas) {
                i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).getClinicaByCnpj(i.getCnpj())).withRel("clinica"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Paciente não encontrado.");
    }
}
