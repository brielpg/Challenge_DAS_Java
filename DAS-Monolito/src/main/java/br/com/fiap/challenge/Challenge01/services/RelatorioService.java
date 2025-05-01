package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoListarRelatorio;
import br.com.fiap.challenge.Challenge01.enums.DasStatus;
import br.com.fiap.challenge.Challenge01.exceptions.ObjectNotFoundException;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import br.com.fiap.challenge.Challenge01.repositories.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Transactional
    public List<DtoListarRelatorio> getAllRelatorio(){
        return relatorioRepository.findAll().stream().map(DtoListarRelatorio::new).toList();
    }

    @Transactional
    public DtoListarRelatorio updateRelatorio(Long id, DtoAtualizarRelatorio dados) {
        if (!relatorioRepository.existsById(id)) throw new ObjectNotFoundException("Relatorio not found");

        var relatorio = relatorioRepository.getReferenceById(id);
        relatorio.atualizarRelatorio(dados);
        this.save(relatorio);
        return new DtoListarRelatorio(relatorio);
    }

    @Transactional
    public void recusarRelatorio(Long id) {
        if (!relatorioRepository.existsById(id)) throw new ObjectNotFoundException("Relatorio not found");

        var relatorio = relatorioRepository.getReferenceById(id);

        if (!relatorio.getStatus().equals(DasStatus.RECUSADO)) {
            relatorio.setStatus(DasStatus.RECUSADO);
            this.save(relatorio);
        }
    }

    @Transactional
    public void aprovarRelatorio(Long id) {
        if (!relatorioRepository.existsById(id)) throw new ObjectNotFoundException("Relatorio not found");

        var relatorio = relatorioRepository.getReferenceById(id);

        if (!relatorio.getStatus().equals(DasStatus.APROVADO)) {
            relatorio.setStatus(DasStatus.APROVADO);
            this.save(relatorio);
        }
    }

    @Transactional
    public DtoListarRelatorio findById(Long id) {
        return relatorioRepository.findById(id)
                .map(DtoListarRelatorio::new)
                .orElseThrow(() -> new ObjectNotFoundException("Relatorio not found"));
    }

    @Transactional
    private void save(Relatorio relatorio){
        relatorioRepository.save(relatorio);
    }
}
