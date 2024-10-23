package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.consulta.DtoAtualizarConsulta;
import br.com.fiap.challenge.Challenge01.dto.consulta.DtoCriarConsulta;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "t_das_consultas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Consulta extends RepresentationModel<Consulta> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String procedimento;
    private String dentista;
    private LocalDate dataConsulta;
    private LocalDate dataEnvioConsulta;
    private BigDecimal valorConsulta;
    @OneToOne
    @JoinColumn(name = "relatorio_id")
    private Relatorio relatorio;
    @ManyToOne
    @JoinColumn(name = "clinica_id")
    private Clinica clinica;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Consulta(DtoCriarConsulta dados){
        this.procedimento = dados.procedimento();
        this.dentista = dados.dentista();
        this.dataConsulta = dados.dataConsulta();
        this.dataEnvioConsulta = LocalDate.now();
        this.valorConsulta = dados.valorConsulta();
    }

    public void atualizarConsulta(DtoAtualizarConsulta dados) {
        if (dados.procedimento() != null){
            this.procedimento = dados.procedimento();
        }
        if (dados.dentista() != null){
            this.dentista = dados.dentista();
        }
        if (dados.dataConsulta() != null){
            this.dataConsulta = dados.dataConsulta();
        }
        if (dados.valorConsulta() != null){
            this.valorConsulta = dados.valorConsulta();
        }
    }
}
