package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.controllers.ClinicaController;
import br.com.fiap.challenge.Challenge01.controllers.ConsultaController;
import br.com.fiap.challenge.Challenge01.controllers.PacienteController;
import br.com.fiap.challenge.Challenge01.controllers.RelatorioController;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoListarClinica;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoAtualizarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoCriarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoListarConsulta;
import br.com.fiap.challenge.Challenge01.models.Consulta;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import br.com.fiap.challenge.Challenge01.repositories.ClinicaRepository;
import br.com.fiap.challenge.Challenge01.repositories.ConsultaRepository;
import br.com.fiap.challenge.Challenge01.repositories.PacienteRepository;
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
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ClinicaRepository clinicaRepository;
    @Autowired
    private RelatorioRepository relatorioRepository;

    @Transactional
    public ResponseEntity<?> createConsulta(DtoCriarConsulta dados) {
        if (!pacienteRepository.existsByCpf(dados.paciente_cpf())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Paciente não encontrado");
        }

        var paciente = pacienteRepository.findByCpf(dados.paciente_cpf());
        var clinica = clinicaRepository.findById(dados.clinica_id())
                .orElseThrow(() -> new RuntimeException("Erro: Clínica não encontrada"));

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

        var retorno = new DtoListarConsulta(consulta);

        retorno.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaController.class).getAllConsultas(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaController.class).getConsulta(consulta.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClinicaController.class).getClinicaByCnpj(consulta.getClinica().getCnpj())).withRel("clinica"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacienteController.class).getPacienteByCpf(consulta.getPaciente().getCpf())).withRel("paciente"),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelatorioController.class).getRelatorio(relatorio.getId())).withRel("relatorio")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(retorno);
    }

    @Transactional
    public ResponseEntity<?> updateConsulta(DtoAtualizarConsulta dados) {
        if (consultaRepository.existsById(dados.id())){
            var consulta = consultaRepository.getReferenceById(dados.id());
            consulta.atualizarConsulta(dados);

            var retorno = new DtoListarConsulta(consulta);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaController.class).getAllConsultas(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION),
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaController.class).getConsulta(consulta.getId())).withSelfRel()
            );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Consulta não encontrada.");
    }

    @Transactional
    public Page<DtoListarConsulta> getAllConsultas(Pageable paginacao) {
        var consultas = consultaRepository.findAll(paginacao).map(DtoListarConsulta::new);
        for (DtoListarConsulta i : consultas){
            i.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaController.class).getConsulta(i.id)).withSelfRel());
        }

        return consultas;
    }

    @Transactional
    public ResponseEntity<?> getConsulta(Long id) {
        var consultaFind = consultaRepository.findById(id);
        if (consultaFind.isPresent()) {
            var consulta = consultaFind.get();
            var retorno = new DtoListarConsulta(consulta);

            retorno.add(
                    WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsultaController.class).getAllConsultas(PageRequest.of(0, 10))).withRel(IanaLinkRelations.COLLECTION)
            );

            return ResponseEntity.status(HttpStatus.OK).body(retorno);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Consulta com este Id não foi encontrada");
    }
}
