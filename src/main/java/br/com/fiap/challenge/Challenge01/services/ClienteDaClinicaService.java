package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.cliente.DadosAtualizarCliente;
import br.com.fiap.challenge.Challenge01.dto.cliente.DadosCriarCliente;
import br.com.fiap.challenge.Challenge01.dto.cliente.DadosListagemCliente;
import br.com.fiap.challenge.Challenge01.models.ClienteDaClinica;
import br.com.fiap.challenge.Challenge01.repositories.ClienteDaClinicaRepository;
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
public class ClienteDaClinicaService {
    @Autowired
    private ClienteDaClinicaRepository clienteDaClinicaRepository;

    @Transactional
    public ResponseEntity<?> criarCliente(@Valid @RequestBody DadosCriarCliente dados) {
        if (!clienteDaClinicaRepository.existsByCpf(dados.cpf())){
            var cliente = clienteDaClinicaRepository.save(new ClienteDaClinica(dados));
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: já existe um cliente cadastrado com esse CPF");
    }

    public Page<DadosListagemCliente> listarTodosClientes(Pageable paginacao) {
        return clienteDaClinicaRepository.findAll(paginacao).map(DadosListagemCliente::new);
    }

    public ResponseEntity<?> listarClientePorCPF(String cpf) {
        var cliente = clienteDaClinicaRepository.findByCpf(cpf);
        if (cliente != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new DadosListagemCliente(cliente));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Paciente com este CPF não encontrado");
    }

    @Transactional
    public ResponseEntity<?> atualizarInformacoesCliente(DadosAtualizarCliente dados) {
        if (clienteDaClinicaRepository.existsByCpf(dados.cpf())){
            var cliente = clienteDaClinicaRepository.getReferenceByCpf(dados.cpf());
            cliente.atualizarCliente(dados);
            return ResponseEntity.status(HttpStatus.OK).body(cliente);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Paciente não encontrado.");
    }
}
