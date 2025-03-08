package br.com.fiap.challenge.Challenge01.services;

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
import org.springframework.data.domain.Pageable;
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

            return ResponseEntity.status(HttpStatus.CREATED).body(new DtoListarPaciente(paciente));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: já existe um paciente cadastrado com esse CPF");
    }

    @Transactional
    public Page<DtoListarPaciente> getAllPacientes(Pageable paginacao) {
        return pacienteRepository.findAll(paginacao).map(DtoListarPaciente::new);
    }

    @Transactional
    public ResponseEntity<?> getPacienteByCpf(String cpf) {
        var paciente = pacienteRepository.findByCpf(cpf);
        if (paciente != null) {

            return ResponseEntity.status(HttpStatus.OK).body(new DtoListarPaciente(paciente));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Paciente com este CPF não encontrado");
    }

    @Transactional
    public ResponseEntity<?> updatePaciente(DtoAtualizarPaciente dados) {
        if (pacienteRepository.existsByCpf(dados.cpf())){
            var paciente = pacienteRepository.getReferenceByCpf(dados.cpf());
            paciente.atualizarPaciente(dados);

            return ResponseEntity.status(HttpStatus.OK).body(new DtoListarPaciente(paciente));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Paciente não encontrado.");
    }
}
