package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoCriarRelatorio;
import br.com.fiap.challenge.Challenge01.enums.DasStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "t_das_relatorios")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Relatorio extends RepresentationModel<Relatorio> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String dentista;
    private LocalDate dataEnvioRelatorio;
    private DasStatus status;
    private String imagem;
    @OneToOne(mappedBy = "relatorio")
    private Consulta consulta;
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Relatorio(DtoCriarRelatorio dados) {
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.status = DasStatus.ANALISE;
        this.dataEnvioRelatorio = LocalDate.now();
        this.imagem = dados.imagem();
    }

    public void atualizarRelatorio(DtoAtualizarRelatorio dados) {
        if (dados.titulo() != null){
            this.titulo = dados.titulo();
        }
        if (dados.descricao() != null){
            this.descricao = dados.descricao();
        }
        if (dados.imagem() != null) {
            this.imagem = dados.imagem();
        }
        if (dados.dentista() != null){
            this.dentista = dados.dentista();
        }
    }
}
