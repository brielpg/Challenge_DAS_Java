package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoListarClinica;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.EnderecoRepository;
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
        if (!clinicaRepository.existsByCnpj(dados.cnpj())){
            var endereco = enderecoRepository.save(new Endereco(dados.endereco()));
            var clinica = new Clinica(dados);
            clinica.setEndereco(endereco);
            this.save(clinica);

            return new DtoListarClinica(clinica);
        }
        return null;
    }

    @Transactional
    public DtoListarClinica updateClinica(Long id, DtoAtualizarClinica dados) {
        if (clinicaRepository.existsById(id)){
            var clinica = clinicaRepository.getReferenceById(id);
            clinica.atualizarClinica(dados);
            this.save(clinica);

            return new DtoListarClinica(clinica);
        }
        return null;
    }

    @Transactional
    public List<DtoListarClinica> getAllClinicas() {
        return clinicaRepository.findAll().stream().map(DtoListarClinica::new).toList();
    }

    @Transactional
    public DtoListarClinica getClinicaByCnpj(String cnpj) {
        var clinica = clinicaRepository.findByCnpj(cnpj);
        if (clinica != null) {

            return new DtoListarClinica(clinica);
        }
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        clinicaRepository.deleteById(id);
    }

    @Transactional
    public DtoListarClinica findById(Long id) {
        Optional<Clinica> clinica = clinicaRepository.findById(id);
        if (clinica.isPresent()){
            return new DtoListarClinica(clinica.get());
        }
        throw new RuntimeException("id not found");
    }

    @Transactional
    private void save(Clinica clinica){
        clinicaRepository.save(clinica);
    }
}
