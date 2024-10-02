package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.clinica.DadosAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosCriarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosListagemClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosRequestLogin;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClinicaService {
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Transactional
    public ResponseEntity<?> criarConta(DadosCriarClinica dados) {
        if (!clinicaRepository.existsByCnpj(dados.cnpj())){
            var clinica = clinicaRepository.save(new Clinica(dados));
            return ResponseEntity.status(HttpStatus.CREATED).body(clinica);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Já existe uma clínica registrada com esse CNPJ");
    }

    @Transactional
    public ResponseEntity<?> logarConta(DadosRequestLogin dados) {
        var clinica = clinicaRepository.findByCnpj(dados.cnpj());
        if (clinica != null && clinica.getSenha().equals(dados.senha())) {
            return ResponseEntity.status(HttpStatus.OK).body(clinica);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: CNPJ ou Senha incorretos.");
    }

    @Transactional
    public ResponseEntity<?> atualizarInformacoesConta(DadosAtualizarClinica dados) {
        if (clinicaRepository.existsById(dados.id())){
            var clinica = clinicaRepository.getReferenceById(dados.id());
            clinica.atualizarClinica(dados);
            return ResponseEntity.status(HttpStatus.OK).body(clinica);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Clínica não encontrado.");
    }

    @Transactional
    public Page<DadosListagemClinica> listarTodasClinicas(Pageable paginacao) {
        return clinicaRepository.findAll(paginacao).map(DadosListagemClinica::new);
    }
}
