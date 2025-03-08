package br.com.fiap.challenge.Challenge01.dto.relatorio;

import br.com.fiap.challenge.Challenge01.dto.consulta.DtoListarConsulta;
import br.com.fiap.challenge.Challenge01.enums.DasStatus;
import br.com.fiap.challenge.Challenge01.models.Relatorio;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

public class DtoListarRelatorio extends RepresentationModel<DtoListarRelatorio>{
        public Long id;
        public String titulo;
        public String descricao;
        public DasStatus status;
        public String dentista;
        public LocalDate dataEnvioRelatorio;
        public String imagem;
        public String clinica;
        public String paciente;
        public DtoListarConsulta consulta;

    public DtoListarRelatorio(Relatorio relatorio){
        this.id = relatorio.getId();
        this.titulo = relatorio.getTitulo();
        this.descricao = relatorio.getDescricao();
        this.status = relatorio.getStatus();
        this.dentista = relatorio.getDentista();
        this.dataEnvioRelatorio = relatorio.getDataEnvioRelatorio();
        this.imagem = relatorio.getImagem();
        this.clinica = relatorio.getClinica().getNome();
        this.paciente = relatorio.getPaciente().getNome();
        this.consulta = new DtoListarConsulta(relatorio.getConsulta());
    }
}
