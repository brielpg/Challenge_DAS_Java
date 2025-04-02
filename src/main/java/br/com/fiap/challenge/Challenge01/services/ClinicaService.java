package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoListarClinica;
import br.com.fiap.challenge.Challenge01.exceptions.ConflictException;
import br.com.fiap.challenge.Challenge01.exceptions.ObjectNotFoundException;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.EnderecoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicaService {
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Transactional
    public DtoListarClinica createClinica(DtoCriarClinica dados) {
        if (clinicaRepository.existsByCnpj(dados.cnpj())) throw new ConflictException("CNPJ already registered");

        var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
        var clinica = new Clinica(dados);
        clinica.setEndereco(endereco);
        this.save(clinica);
        return new DtoListarClinica(clinica);
    }

    @Transactional
    public DtoListarClinica updateClinica(Long id, @Valid DtoAtualizarClinica dados) {
        if (!clinicaRepository.existsById(id)) throw new ObjectNotFoundException("Clinica not found");
        var clinica = clinicaRepository.getReferenceById(id);
        clinica.atualizarClinica(dados);
        this.save(clinica);

        return new DtoListarClinica(clinica);
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
}
