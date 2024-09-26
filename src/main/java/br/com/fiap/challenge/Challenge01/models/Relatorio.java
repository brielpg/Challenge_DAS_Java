package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DadosCriarRelatorio;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "t_das_relatorios")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Relatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String medico;
    private String dataConsulta;
    private LocalDate dataEnvioRelatorio;
    private BigDecimal valorConsulta;
    private String status; // APROVADO, RECUSADO, ANALISE
    private String imagem;
    @ManyToOne
    private Clinica clinica;
    @ManyToOne
    private ClienteDaClinica cliente;

    public Relatorio(DadosCriarRelatorio dados) {
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.status = "ANALISE";
        this.medico = dados.medico();
        this.dataConsulta = dados.dataConsulta();
        this.dataEnvioRelatorio = LocalDate.now();;
        this.valorConsulta = dados.valorConsulta();
        this.imagem = dados.imagem();
    }

    public void atualizarRelatorio(DadosAtualizarRelatorio dados) {
        if (dados.titulo() != null){
            this.titulo = dados.titulo();
        }
        if (dados.descricao() != null){
            this.descricao = dados.descricao();
        }
        if (dados.imagem() != null) {
            this.imagem = dados.imagem();
        }
        if (dados.medico() != null){
            this.medico = dados.medico();
        }
        if (dados.dataConsulta() != null){
            this.dataConsulta = dados.dataConsulta();
        }
        if (dados.valorConsulta() != null){
            this.valorConsulta = dados.valorConsulta();
        }
    }
}
