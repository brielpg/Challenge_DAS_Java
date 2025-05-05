package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.EmailMessageDto;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoListarClinica;
import br.com.fiap.challenge.Challenge01.enums.DasMessageType;
import br.com.fiap.challenge.Challenge01.enums.DasRoles;
import br.com.fiap.challenge.Challenge01.exceptions.ConflictException;
import br.com.fiap.challenge.Challenge01.exceptions.ObjectNotFoundException;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.EnderecoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClinicaService implements UserDetailsService {
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EmailProducer emailProducer;

    @Transactional
    public DtoListarClinica createClinica(DtoCriarClinica dados) {
        if (clinicaRepository.existsByCnpj(dados.cnpj())) throw new ConflictException("CNPJ already registered");

        var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
        var clinica = new Clinica(dados, senhaEncriptada(dados.senha()));
        clinica.setEndereco(endereco);
        this.save(clinica);

        this.sendEmail(clinica.getEmail(), clinica.getNome());

        return new DtoListarClinica(clinica);
    }

    @Transactional
    public DtoListarClinica updateClinica(Long id, @Valid DtoAtualizarClinica dados) {
        if (!clinicaRepository.existsById(id)) throw new ObjectNotFoundException("Clinica not found");
        var clinica = clinicaRepository.getReferenceById(id);
        clinica.atualizarClinica(dados, senhaEncriptada(dados.senha()));
        this.save(clinica);

        return new DtoListarClinica(clinica);
    }

    @Transactional
    public void changeRole(Long id, String role) {
        var clinica = clinicaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Clinica not found"));
        clinica.setRole(DasRoles.valueOf(role));
        this.save(clinica);
    }

    private String senhaEncriptada(String senha){
        if (senha != null) return passwordEncoder.encode(senha);
        return null;
    }

    @Transactional
    public List<DtoListarClinica> getAllClinicas() {
        return clinicaRepository.findAll().stream().map(DtoListarClinica::new).toList();
    }

    @Transactional
    public DtoListarClinica getClinicaByCnpj(String cnpj) {
        var clinica = clinicaRepository.findByCnpj(cnpj);
        if (clinica == null) throw new ObjectNotFoundException("Clinica not found");
        return new DtoListarClinica(clinica);
    }

    @Transactional
    public DtoListarClinica getClinicaByEmail(String email){
        var clinica = clinicaRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("Clinica not found"));

        return new DtoListarClinica((Clinica) clinica);
    }

    @Transactional
    public Clinica getClinicaEntityById(Long id){
        return clinicaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Clinica not found"));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!clinicaRepository.existsById(id)) throw new ObjectNotFoundException("Clinica not found");
        clinicaRepository.deleteById(id);
    }

    @Transactional
    public DtoListarClinica findById(Long id) {
        return clinicaRepository.findById(id)
                .map(DtoListarClinica::new)
                .orElseThrow(() -> new ObjectNotFoundException("Clinica not found"));
    }

    @Transactional
    private void save(Clinica clinica){
        clinicaRepository.save(clinica);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clinicaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Clinica not found with email: " + username));
    }

    private void sendEmail(String email, String nome){
        var titulo = "Bem-vindo à nossa plataforma!";
        var mensagem = "Olá "+nome+",\n" +
                "Estamos muito felizes em tê-los conosco! A partir de agora, sua clínica poderá aproveitar todos os benefícios da nossa plataforma para facilitar o gerenciamento e o atendimento aos seus pacientes.\n" +
                "Caso precise de qualquer ajuda, nossa equipe está à disposição.\n" +
                "Bem-vindos à comunidade!\n" +
                "Atenciosamente, Dental Analytics Safe";
        var emailDto = new EmailMessageDto(email, titulo, mensagem, DasMessageType.CLINICA_CADASTRADA.name());
        emailProducer.sendEmailMessage(emailDto);
    }
}
