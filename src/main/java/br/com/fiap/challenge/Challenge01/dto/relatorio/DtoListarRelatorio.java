package br.com.fiap.challenge.Challenge01.dto.relatorio;

import br.com.fiap.challenge.Challenge01.models.Paciente;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.DasStatus;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import org.springframework.hateoas.RepresentationModel;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DtoListarRelatorio extends RepresentationModel<DtoListarRelatorio>{
        public Long id;
        public String titulo;
        public String descricao;
        public DasStatus status;
        public String medico;
        public LocalDate dataConsulta;
        public LocalDate dataEnvioRelatorio;
        public BigDecimal valorConsulta;
        public String imagem;
        public Clinica clinica;
        public Paciente cliente;

    public DtoListarRelatorio(Relatorio relatorio){
        this.id = relatorio.getId();
        this.titulo = relatorio.getTitulo();
        this.descricao = relatorio.getDescricao();
        this.status = relatorio.getStatus();
        this.medico = relatorio.getMedico();
        this.dataConsulta = relatorio.getDataConsulta();
        this.dataEnvioRelatorio = relatorio.getDataEnvioRelatorio();
        this.valorConsulta = relatorio.getValorConsulta();
        this.imagem = relatorio.getImagem();
        this.clinica = relatorio.getClinica();
        this.cliente = relatorio.getCliente();
    }
}
