package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoListarRelatorio;
import br.com.fiap.challenge.Challenge01.enums.DasStatus;
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

    @Transactional
    public Page<DtoListarRelatorio> getAllRelatorio(Pageable paginacao){
        return relatorioRepository.findAll(paginacao).map(DtoListarRelatorio::new);
    }

    @Transactional
    public Page<DtoListarRelatorio> getRelatorioByClinica(Long clinica_id, Pageable paginacao){
        return relatorioRepository.findByClinica_Id(clinica_id, paginacao).map(DtoListarRelatorio::new);
    }

    @Transactional
    public Page<DtoListarRelatorio> getRelatorioByPaciente(Long paciente_id, Pageable paginacao) {

        return relatorioRepository.findByPaciente_Id(paciente_id, paginacao).map(DtoListarRelatorio::new);
    }

    @Transactional
    public ResponseEntity<?> updateRelatorio(DtoAtualizarRelatorio dados) {
        if (relatorioRepository.existsById(dados.id())){
            var relatorio = relatorioRepository.getReferenceById(dados.id());
            relatorio.atualizarRelatorio(dados);

            return ResponseEntity.status(HttpStatus.OK).body(new DtoListarRelatorio(relatorio));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> recusarRelatorio(Long id) {
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);

            if (!relatorio.getStatus().equals(DasStatus.RECUSADO)){
                relatorio.setStatus(DasStatus.RECUSADO);

                return ResponseEntity.status(HttpStatus.OK).body(new DtoListarRelatorio(relatorio));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório já foi RECUSADO.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> aprovarRelatorio(Long id) {
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);

            if (!relatorio.getStatus().equals(DasStatus.APROVADO)){
                relatorio.setStatus(DasStatus.APROVADO);

                return ResponseEntity.status(HttpStatus.OK).body(new DtoListarRelatorio(relatorio));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório já foi APROVADO.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> getRelatorio(Long id) {
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);

            return ResponseEntity.status(HttpStatus.OK).body(new DtoListarRelatorio(relatorio));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }
}
