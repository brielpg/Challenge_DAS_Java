package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.controllers.ClienteDaClinicaController;
import br.com.fiap.challenge.Challenge01.controllers.ClinicaController;
import br.com.fiap.challenge.Challenge01.controllers.RelatorioController;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosCriarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosListagemRelatorio;
import br.com.fiap.challenge.Challenge01.models.DasStatus;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import br.com.fiap.challenge.Challenge01.repositories.ClienteDaClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
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
    @Autowired
    private ClienteDaClinicaRepository clienteDaClinicaRepository;
    @Autowired
    private ClinicaRepository clinicaRepository;

    @Transactional
    public ResponseEntity<?> createRelatorio(DadosCriarRelatorio dados) {
        if (!clienteDaClinicaRepository.existsByCpf(dados.cliente_cpf())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Cliente não encontrado");
        }

        var cliente = clienteDaClinicaRepository.findByCpf(dados.cliente_cpf());
        var clinica = clinicaRepository.findById(dados.clinica_id())
                .orElseThrow(() -> new RuntimeException("Erro: Clínica não encontrada"));

        // Criei um novo relatorio e setei nele os respectivos cliente e clinica.
        var relatorio = new Relatorio(dados);
        relatorio.setCliente(cliente);
        relatorio.setClinica(clinica);

        cliente.setQtdConsultas(cliente.getQtdConsultas()+1);
        cliente.adicionarClinica(clinica);
        clinica.adicionarCliente(cliente);

        relatorioRepository.save(relatorio);

        var retorno = new DadosListagemRelatorio(relatorio);

        retorno.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getAllRelatorio(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
        );

        retorno.clinica.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(relatorio.getClinica().getCnpj())).withRel("clinica")
        );

        retorno.cliente.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteDaClinicaController.class).listarClientePorCPF(relatorio.getCliente().getCpf())).withRel("cliente")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
    }

    @Transactional
    public Page<DadosListagemRelatorio> getAllRelatorio(Pageable paginacao){
        var relatorios = relatorioRepository.findAll(paginacao).map(DadosListagemRelatorio::new);
        for (DadosListagemRelatorio i : relatorios) {
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(i.id)).withSelfRel());
        }

        return relatorios;
    }

    @Transactional
    public Page<DadosListagemRelatorio> getRelatorioByClinica(Long clinica_id, Pageable paginacao){
        var relatorios = relatorioRepository.findByClinica_Id(clinica_id, paginacao).map(DadosListagemRelatorio::new);
        relatorios.forEach(i -> {
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(i.id)).withSelfRel());
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(i.clinica.getCnpj())).withRel("clinica"));
        });

        return relatorios;
    }

    @Transactional
    public Page<DadosListagemRelatorio> getRelatorioByCliente(Long cliente_id, Pageable paginacao) {
        var relatorios = relatorioRepository.findByCliente_Id(cliente_id, paginacao).map(DadosListagemRelatorio::new);
        relatorios.forEach(i -> {
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(i.id)).withSelfRel());
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteDaClinicaController.class).listarClientePorCPF(i.cliente.getCpf())).withRel("cliente"));
        });

        return relatorios;
    }

    @Transactional
    public ResponseEntity<?> updateRelatorio(DadosAtualizarRelatorio dados) {
        if (relatorioRepository.existsById(dados.id())){
            var relatorio = relatorioRepository.getReferenceById(dados.id());
            relatorio.atualizarRelatorio(dados);
            var retorno = new DadosListagemRelatorio(relatorio);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel()
            );

            retorno.clinica.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(relatorio.getClinica().getCnpj())).withRel("clinica")
            );

            retorno.cliente.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteDaClinicaController.class).listarClientePorCPF(relatorio.getCliente().getCpf())).withRel("cliente")
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
                var retorno = new DadosListagemRelatorio(relatorio);

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
                var retorno = new DadosListagemRelatorio(relatorio);

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
            var retorno = new DadosListagemRelatorio(relatorio);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withSelfRel(),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getAllRelatorio(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
            );

            if (retorno.status.equals(DasStatus.APROVADO)){
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).recusarRelatorio(relatorio.getId())).withRel("recusar"));
            }else if (retorno.status.equals(DasStatus.RECUSADO)) {
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).aprovarRelatorio(relatorio.getId())).withRel("aprovar"));
            }else{
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).recusarRelatorio(relatorio.getId())).withRel("recusar"));
                retorno.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).aprovarRelatorio(relatorio.getId())).withRel("aprovar"));
            }

            retorno.clinica.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).listarClinicaPorCnpj(relatorio.getClinica().getCnpj())).withRel("clinica")
            );

            retorno.cliente.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteDaClinicaController.class).listarClientePorCPF(relatorio.getCliente().getCpf())).withRel("cliente")
            );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Relatório não encontrado.");
    }
}
