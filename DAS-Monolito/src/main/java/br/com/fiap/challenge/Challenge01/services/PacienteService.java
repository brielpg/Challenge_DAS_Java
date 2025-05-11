package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.EmailMessageDto;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoListarPaciente;
import br.com.fiap.challenge.Challenge01.enums.DasMessageType;
import br.com.fiap.challenge.Challenge01.enums.DasRoles;
import br.com.fiap.challenge.Challenge01.enums.DasStatus;
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

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClinicaService clinicaService;

    @Autowired
    private EmailProducer emailProducer;

    @Transactional
    public DtoListarPaciente createPaciente(DtoCriarPaciente dados, String emailClinica) {
        if (pacienteRepository.existsByCpf(dados.cpf())) throw new ConflictException("Cpf already registered");
        if (dados.dataNascimento().isAfter(LocalDate.now())) throw new InvalidDataException();
        var dtoClinica = clinicaService.getClinicaByEmail(emailClinica);
        var clinica = clinicaService.getClinicaEntityById(dtoClinica.id);

        var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
        var paciente = new Paciente(dados);
        paciente.setEndereco(endereco);
        paciente.adicionarClinica(clinica);
        this.save(paciente);

        this.sendEmail(paciente.getEmail(), paciente.getNome(), clinica.getEmail(), clinica.getNome(), true);
        this.sendEmail(paciente.getEmail(), paciente.getNome(), clinica.getEmail(), clinica.getNome(), false);

        return new DtoListarPaciente(paciente);
    }

    @Transactional
    public List<DtoListarPaciente> getAllPacientes(String emailClinicaLogada) {
        var dtoClinica = clinicaService.getClinicaByEmail(emailClinicaLogada);
        var clinica = clinicaService.getClinicaEntityById(dtoClinica.id);

        if (clinica.getRole().equals(DasRoles.ADMIN)) {
            return pacienteRepository.findAll().stream()
                    .filter(Paciente::getAtivo)
                    .map(DtoListarPaciente::new)
                    .toList();
        }

        return pacienteRepository.findByClinicasContaining(clinica).stream()
                .filter(Paciente::getAtivo)
                .map(DtoListarPaciente::new)
                .toList();
    }

    @Transactional
    public DtoListarPaciente getPacienteByCpf(String cpf) {
        var paciente = pacienteRepository.findByCpf(cpf);

        if (paciente == null || !paciente.getAtivo()) throw new ObjectNotFoundException("Pacient not found");

        return new DtoListarPaciente(paciente);
    }

    @Transactional
    public DtoListarPaciente updatePaciente(String cpf, DtoAtualizarPaciente dados) {
        if (!pacienteRepository.existsByCpf(cpf)) throw new ObjectNotFoundException("Pacient not found");
        if (dados.dataNascimento().isAfter(LocalDate.now())) throw new InvalidDataException();

        var paciente = pacienteRepository.getReferenceByCpf(cpf);
        if (!paciente.getAtivo()) throw new ObjectNotFoundException("Pacient not found");

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
        var paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pacient not found"));

        paciente.setAtivo(false);
        pacienteRepository.save(paciente);
    }

    private void sendEmail(String emailPaciente, String nomePaciente, String emailClinica, String nomeClinica, boolean isClinica){
        if (isClinica) {
            var titulo = "Novo paciente cadastrado com sucesso!";
            var mensagem = "Olá " + nomeClinica + ",\n" +
                    "Queremos informar que o paciente "+nomePaciente+" foi cadastrado com sucesso na nossa plataforma. Agora, vocês podem utilizar as ferramentas disponíveis para melhorar ainda mais o atendimento.\n" +
                    "Qualquer dúvida, estamos aqui para ajudar.\n" +
                    "Atenciosamente, Dental Analytics Safe";
            var emailDto = new EmailMessageDto(emailClinica, titulo, mensagem, DasMessageType.PACIENTE_CADASTRADO.name());
            emailProducer.sendEmailMessage(emailDto);
        } else {
            var titulo = "Bem-vindo(a) ao Dental Analytics Safe";
            var mensagem = "Olá " + nomePaciente + ",\n" +
                    "A clínica "+nomeClinica+" acaba de cadastrá-lo na nossa plataforma!\n" +
                    "Estamos à disposição para qualquer dúvida. Seja bem-vindo(a)!\n" +
                    "Atenciosamente, Dental Analytics Safe";
            var emailDto = new EmailMessageDto(emailPaciente, titulo, mensagem, DasMessageType.PACIENTE_CADASTRADO.name());
            emailProducer.sendEmailMessage(emailDto);
        }
    }
}
