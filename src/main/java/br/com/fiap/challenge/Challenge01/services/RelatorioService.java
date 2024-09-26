package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosCriarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosListagemRelatorio;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import br.com.fiap.challenge.Challenge01.repositories.ClienteDaClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;
    @Autowired
    private ClienteDaClinicaRepository clienteDaClinicaRepository;
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Transactional
    public ResponseEntity<?> criarRelatorio(DadosCriarRelatorio dados) {
        if (!clienteDaClinicaRepository.existsByCpf(dados.cliente_cpf())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Cliente não encontrado");
        }

        var cliente = clienteDaClinicaRepository.findByCpf(dados.cliente_cpf());
        var clinica = clinicaRepository.findById(dados.clinica_id())
                .orElseThrow(() -> new RuntimeException("Erro: Clínica não encontrada"));

        var relatorio = relatorioRepository.save(new Relatorio(dados));
        cliente.setQtdConsultas(cliente.getQtdConsultas()+1);
        relatorio.setCliente(cliente);
        relatorio.setClinica(clinica);
        return ResponseEntity.status(HttpStatus.CREATED).body(relatorio);
    }

    public Page<DadosListagemRelatorio> listarTodosRelatorios(Pageable paginacao){
        return relatorioRepository.findAll(paginacao).map(DadosListagemRelatorio::new);
    }

    public Page<DadosListagemRelatorio> listarRelatoriosPorClinica(Long clinicaId, Pageable paginacao){
        return relatorioRepository.findByClinicaId(clinicaId, paginacao).map(DadosListagemRelatorio::new);
    }

    public Page<DadosListagemRelatorio> listarRelatoriosPorCliente(Long idCliente, Pageable paginacao) {
        return relatorioRepository.findByClienteId(idCliente, paginacao).map(DadosListagemRelatorio::new);
    }

    @Transactional
    public ResponseEntity<?> atualizarInformacoesRelatorio(DadosAtualizarRelatorio dados) {
        if (relatorioRepository.existsById(dados.id())){
            var relatorio = relatorioRepository.getReferenceById(dados.id());
            relatorio.atualizarRelatorio(dados);
            return ResponseEntity.status(HttpStatus.OK).body("Relatório ATUALIZADO com Sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> recusarRelatorio(Long id) {
        var status = "RECUSADO";
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);

            if (!relatorio.getStatus().equals(status)){
                relatorio.setStatus(status);
                return ResponseEntity.status(HttpStatus.OK).body("Relatório RECUSADO com Sucesso!");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório já foi RECUSADO.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> aprovarRelatorio(Long id) {
        var status = "APROVADO";
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);

            if (!relatorio.getStatus().equals(status)){
                relatorio.setStatus(status);
                return ResponseEntity.status(HttpStatus.OK).body("Relatório APROVADO com Sucesso!");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório já foi APROVADO.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório não encontrado.");
    }
}
