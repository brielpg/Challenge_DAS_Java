package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.EmailMessageDto;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoAtualizarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoCriarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoListarConsulta;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoListarPaciente;
import br.com.fiap.challenge.Challenge01.enums.DasMessageType;
import br.com.fiap.challenge.Challenge01.enums.DasRoles;
import br.com.fiap.challenge.Challenge01.exceptions.InvalidDataException;
import br.com.fiap.challenge.Challenge01.exceptions.ObjectNotFoundException;
import br.com.fiap.challenge.Challenge01.models.Consulta;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.ConsultaRepository;
import br.com.fiap.challenge.Challenge01.repositories.PacienteRepository;
import br.com.fiap.challenge.Challenge01.repositories.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ClinicaRepository clinicaRepository;
    @Autowired
    private ClinicaService clinicaService;
    @Autowired
    private RelatorioRepository relatorioRepository;
    @Autowired
    private EmailProducer emailProducer;

    @Transactional
    public DtoListarConsulta createConsulta(DtoCriarConsulta dados) {
        if (!pacienteRepository.existsByCpf(dados.paciente_cpf())) throw new ObjectNotFoundException("Paciente not found");
        if (!clinicaRepository.existsById(dados.clinica_id())) throw new ObjectNotFoundException("Clinica not found");
        if (dados.dataConsulta().isAfter(LocalDate.now())) throw new InvalidDataException();

        var paciente = pacienteRepository.getReferenceByCpf(dados.paciente_cpf());
        var clinica = clinicaRepository.getReferenceById(dados.clinica_id());

        var relatorio = new Relatorio(dados.relatorio());
        var consulta = new Consulta(dados);

        paciente.setQtdConsultas(paciente.getQtdConsultas()+1);
        paciente.adicionarClinica(clinica);
        clinica.adicionarPaciente(paciente);

        consulta.setPaciente(paciente);
        consulta.setClinica(clinica);
        relatorio.setPaciente(paciente);
        relatorio.setClinica(clinica);
        relatorio.setDentista(consulta.getDentista());

        consulta.setRelatorio(relatorio);

        consultaRepository.save(consulta);
        relatorioRepository.save(relatorio);

        this.sendEmail(clinica.getEmail(), clinica.getNome(), paciente.getNome());

        return new DtoListarConsulta(consulta);
    }

    @Transactional
    public DtoListarConsulta updateConsulta(Long id, DtoAtualizarConsulta dados) {
        if (!consultaRepository.existsById(id)) throw new ObjectNotFoundException("Consulta not found");
        if (dados.dataConsulta().isAfter(LocalDate.now())) throw new InvalidDataException();

        var consulta = consultaRepository.getReferenceById(id);
        consulta.atualizarConsulta(dados);
        this.save(consulta);
        return new DtoListarConsulta(consulta);
    }

    @Transactional
    public List<DtoListarConsulta> getAllConsultas(String emailClinicaLogada) {
        var dtoClinica = clinicaService.getClinicaByEmail(emailClinicaLogada);
        var clinica = clinicaService.getClinicaEntityById(dtoClinica.id);

        if (clinica.getRole().equals(DasRoles.ADMIN)) {
            return consultaRepository.findAll().stream()
                    .map(DtoListarConsulta::new)
                    .toList();
        }

        return consultaRepository.findByClinica(clinica).stream()
                .map(DtoListarConsulta::new)
                .toList();
    }

    @Transactional
    public DtoListarConsulta findById(Long id) {
        return consultaRepository.findById(id)
                .map(DtoListarConsulta::new)
                .orElseThrow(() -> new ObjectNotFoundException("Consulta not found"));
    }

    @Transactional
    private void save(Consulta consulta){
        consultaRepository.save(consulta);
    }

    private void sendEmail(String email, String nomeClinica, String nomePaciente){
        var titulo = "Novo relatório gerado para o paciente " + nomePaciente;
        var mensagem = "Olá " + nomeClinica + ",\n" +
                    "Informamos que um novo relatório da consulta do paciente " + nomePaciente + " foi gerado e está disponível na plataforma.\n" +
                    "Se precisar de algo, não hesite em nos contatar.\n" +
                    "Atenciosamente, Dental Analytics Safe";

        var emailDto = new EmailMessageDto(email, titulo, mensagem, DasMessageType.CONSULTA_REALIZADA.name());
        emailProducer.sendEmailMessage(emailDto);
    }
}
