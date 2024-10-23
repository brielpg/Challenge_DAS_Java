package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.controllers.PacienteController;
import br.com.fiap.challenge.Challenge01.controllers.ClinicaController;
import br.com.fiap.challenge.Challenge01.controllers.RelatorioController;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoListarRelatorio;
import br.com.fiap.challenge.Challenge01.enums.DasStatus;
import br.com.fiap.challenge.Challenge01.repositories.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        var relatorios = relatorioRepository.findAll(paginacao).map(DtoListarRelatorio::new);
        for (DtoListarRelatorio i : relatorios) {
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(i.id)).withSelfRel());
        }

        return relatorios;
    }

    @Transactional
    public Page<DtoListarRelatorio> getRelatorioByClinica(Long clinica_id, Pageable paginacao){
        var relatorios = relatorioRepository.findByClinica_Id(clinica_id, paginacao).map(DtoListarRelatorio::new);
        relatorios.forEach(i -> {
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(i.id)).withSelfRel());
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).getClinicaByCnpj(i.clinica)).withRel("clinica"));
        });

        return relatorios;
    }

    @Transactional
    public Page<DtoListarRelatorio> getRelatorioByPaciente(Long paciente_id, Pageable paginacao) {
        var relatorios = relatorioRepository.findByPaciente_Id(paciente_id, paginacao).map(DtoListarRelatorio::new);
        relatorios.forEach(i -> {
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(i.id)).withSelfRel());
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(i.paciente)).withRel("paciente"));
        });

        return relatorios;
    }

    @Transactional
    public ResponseEntity<?> updateRelatorio(DtoAtualizarRelatorio dados) {
        if (relatorioRepository.existsById(dados.id())){
            var relatorio = relatorioRepository.getReferenceById(dados.id());
            relatorio.atualizarRelatorio(dados);
            var retorno = new DtoListarRelatorio(relatorio);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).getClinicaByCnpj(relatorio.getClinica().getCnpj())).withRel("clinica"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(relatorio.getPaciente().getCpf())).withRel("paciente")
            );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> recusarRelatorio(Long id) {
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);

            if (!relatorio.getStatus().equals(DasStatus.RECUSADO)){
                relatorio.setStatus(DasStatus.RECUSADO);
                var retorno = new DtoListarRelatorio(relatorio);

                retorno.add(
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getAllRelatorio(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
                );

                return ResponseEntity.status(HttpStatus.OK).body(retorno);
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
                var retorno = new DtoListarRelatorio(relatorio);

                retorno.add(
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getAllRelatorio(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
                );

                return ResponseEntity.status(HttpStatus.OK).body(retorno);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Relatório já foi APROVADO.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }

    @Transactional
    public ResponseEntity<?> getRelatorio(Long id) {
        if (relatorioRepository.existsById(id)){
            var relatorio = relatorioRepository.getReferenceById(id);
            var retorno = new DtoListarRelatorio(relatorio);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getAllRelatorio(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).getClinicaByCnpj(relatorio.getClinica().getCnpj())).withRel("clinica"),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(relatorio.getPaciente().getCpf())).withRel("paciente")
            );

            if (retorno.status.equals(DasStatus.APROVADO)){
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).recusarRelatorio(relatorio.getId())).withRel("recusar"));
            }else if (retorno.status.equals(DasStatus.RECUSADO)) {
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).aprovarRelatorio(relatorio.getId())).withRel("aprovar"));
            }else{
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).recusarRelatorio(relatorio.getId())).withRel("recusar"));
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).aprovarRelatorio(relatorio.getId())).withRel("aprovar"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }
}
