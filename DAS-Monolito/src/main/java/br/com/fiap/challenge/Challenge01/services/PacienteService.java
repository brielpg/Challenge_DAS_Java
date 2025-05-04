package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.EmailMessageDto;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoListarPaciente;
import br.com.fiap.challenge.Challenge01.exceptions.ConflictException;
import br.com.fiap.challenge.Challenge01.exceptions.InvalidDataException;
import br.com.fiap.challenge.Challenge01.exceptions.ObjectNotFoundException;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.models.Paciente;
import br.com.fiap.challenge.Challenge01.repositories.EnderecoRepository;
import br.com.fiap.challenge.Challenge01.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EmailProducer emailProducer;

    @Transactional
    public DtoListarPaciente createPaciente(DtoCriarPaciente dados) {
        if (pacienteRepository.existsByCpf(dados.cpf())) throw new ConflictException("Cpf already registered");
        if (dados.dataNascimento().isAfter(LocalDate.now())) throw new InvalidDataException();

        var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
        var paciente = new Paciente(dados);
        paciente.setEndereco(endereco);
        this.save(paciente);

        this.sendEmail(paciente.getEmail(), paciente.getNome());

        return new DtoListarPaciente(paciente);
    }

    @Transactional
    public List<DtoListarPaciente> getAllPacientes() {
        return pacienteRepository.findAll().stream().map(DtoListarPaciente::new).toList();
    }

    @Transactional
    public DtoListarPaciente getPacienteByCpf(String cpf) {
        var paciente = pacienteRepository.findByCpf(cpf);

        if (paciente == null) throw new ObjectNotFoundException("Pacient not found");

        return new DtoListarPaciente(paciente);
    }

    @Transactional
    public DtoListarPaciente updatePaciente(String cpf, DtoAtualizarPaciente dados) {
        if (!pacienteRepository.existsByCpf(cpf)) throw new ObjectNotFoundException("Pacient not found");

        var paciente = pacienteRepository.getReferenceByCpf(cpf);
        paciente.atualizarPaciente(dados);
        this.save(paciente);
        return new DtoListarPaciente(paciente);
    }

    @Transactional
    private void save(Paciente paciente){
        pacienteRepository.save(paciente);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!pacienteRepository.existsById(id)) throw new ObjectNotFoundException("Pacient not found");
        pacienteRepository.deleteById(id);
    }

    private void sendEmail(String email, String nome){
        var titulo = "Bem-vindo(a) ao Dental Analytics Safe";
        var mensagem = "Olá " + nome + ",\n" +
                "Você acaba de ser cadastrado na nossa plataforma!\n" +
                "Estamos à disposição para qualquer dúvida. Seja bem-vindo(a)!\n" +
                "Atenciosamente, Dental Analytics Safe";

        var emailDto = new EmailMessageDto(email, titulo, mensagem);
        emailProducer.sendEmailMessage(emailDto);
    }
}
