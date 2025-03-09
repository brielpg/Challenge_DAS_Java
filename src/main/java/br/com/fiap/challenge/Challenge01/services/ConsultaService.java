package br.com.fiap.challenge.Challenge01.services;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public DtoListarConsulta createConsulta(DtoCriarConsulta dados) {
        if (!pacienteRepository.existsByCpf(dados.paciente_cpf())) return null;
        if (!clinicaRepository.existsById(dados.clinica_id())) return null;

        var paciente = pacienteRepository.getReferenceByCpf(dados.paciente_cpf());
        var clinica = clinicaRepository.getReferenceById(dados.clinica_id());

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

        return new DtoListarConsulta(consulta);
    }

    @Transactional
    public DtoListarConsulta updateConsulta(Long id, DtoAtualizarConsulta dados) {
        if (consultaRepository.existsById(id)){
            var consulta = consultaRepository.getReferenceById(id);
            consulta.atualizarConsulta(dados);
            this.save(consulta);

            return new DtoListarConsulta(consulta);
        }
        return null;
    }

    @Transactional
    public List<DtoListarConsulta> getAllConsultas() {
        return consultaRepository.findAll().stream().map(DtoListarConsulta::new).toList();
    }

    @Transactional
    public DtoListarConsulta getConsultaById(Long id) {
        var consultaFind = consultaRepository.findById(id);
        if (consultaFind.isPresent()) {
            var consulta = consultaFind.get();

            return new DtoListarConsulta(consulta);
        }
        return null;
    }

    @Transactional
    public DtoListarConsulta findById(Long id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        if (consulta.isPresent()){
            return new DtoListarConsulta(consulta.get());
        }
        throw new RuntimeException("id not found");
    }

    @Transactional
    private void save(Consulta consulta){
        consultaRepository.save(consulta);
    }
}
