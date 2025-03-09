package br.com.fiap.challenge.Challenge01.dto.consulta;

import br.com.fiap.challenge.Challenge01.models.Consulta;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DtoListarConsulta extends RepresentationModel<DtoListarConsulta> {
    public Long id;
    public String procedimento;
    public String dentista;
    public LocalDate dataConsulta;
    public LocalDate dataEnvioConsulta;
    public BigDecimal valorConsulta;
    public Long relatorio;
    public String clinica;
    public String paciente;

    public DtoListarConsulta(Consulta consulta){
        this.id = consulta.getId();
        this.procedimento = consulta.getProcedimento();
        this.dentista = consulta.getDentista();
        this.dataConsulta = consulta.getDataConsulta();
        this.dataEnvioConsulta = consulta.getDataEnvioConsulta();
        this.valorConsulta = consulta.getValorConsulta();
        this.relatorio = consulta.getRelatorio().getId();
        this.clinica = consulta.getClinica().getNome();
        this.paciente = consulta.getPaciente().getNome();
    }
}
