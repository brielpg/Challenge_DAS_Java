package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.controllers.PacienteController;
import br.com.fiap.challenge.Challenge01.controllers.ClinicaController;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoListarPaciente;
import br.com.fiap.challenge.Challenge01.models.Paciente;
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
    private PacienteRepository clienteDaClinicaRepository;

    @Transactional
    public ResponseEntity<?> criarCliente(@Valid @RequestBody DtoCriarPaciente dados) {
        if (!clienteDaClinicaRepository.existsByCpf(dados.cpf())){
            var cliente = clienteDaClinicaRepository.save(new Paciente(dados));
            var retorno = new DtoListarPaciente(cliente);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarTodosClientes(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarClientePorCPF(cliente.getCpf())).withSelfRel()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: já existe um cliente cadastrado com esse CPF");
    }

    @Transactional
    public Page<DtoListarPaciente> listarTodosClientes(Pageable paginacao) {
        var clientes = clienteDaClinicaRepository.findAll(paginacao).map(DtoListarPaciente::new);

        clientes.forEach(i -> i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarClientePorCPF(i.cpf)).withSelfRel()));

        return clientes;
    }

    @Transactional
    public ResponseEntity<?> listarClientePorCPF(String cpf) {
        var cliente = clienteDaClinicaRepository.findByCpf(cpf);
        if (cliente != null) {
            var retorno = new DtoListarPaciente(cliente);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarTodosClientes(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
            );

            for (var i: retorno.clinicas) {
                i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(i.getCnpj())).withRel("clinica"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Paciente com este CPF não encontrado");
    }

    @Transactional
    public ResponseEntity<?> atualizarInformacoesCliente(DtoAtualizarPaciente dados) {
        if (clienteDaClinicaRepository.existsByCpf(dados.cpf())){
            var cliente = clienteDaClinicaRepository.getReferenceByCpf(dados.cpf());
            cliente.atualizarCliente(dados);

            var retorno = new DtoListarPaciente(cliente);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).listarClientePorCPF(cliente.getCpf())).withSelfRel()
            );

            for (var i : retorno.clinicas) {
                i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(i.getCnpj())).withRel("clinica"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Paciente não encontrado.");
    }
}
