package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.paciente.DtoAtualizarPaciente;
import br.com.fiap.challenge.Challenge01.dto.paciente.DtoCriarPaciente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_das_pacientes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String nmrCarteiraOdonto;
    private LocalDate dataNascimento;
    private Integer qtdConsultas;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "paciente_clinica", joinColumns = @JoinColumn(name = "paciente_id"), inverseJoinColumns = @JoinColumn(name = "clinica_id"))
    private List<Clinica> clinicas = new ArrayList<>();

    public Paciente(DtoCriarPaciente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.dataNascimento = dados.dataNascimento();
        this.nmrCarteiraOdonto = dados.nmrCarteiraOdonto();
        this.qtdConsultas = 0;
    }

    public void atualizarPaciente(DtoAtualizarPaciente dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.dataNascimento() != null){
            this.dataNascimento = dados.dataNascimento();
        }
        if (dados.email() != null){
            this.email = dados.email();
        }
        if (dados.nmrCarteiraOdonto() != null){
            this.nmrCarteiraOdonto = dados.nmrCarteiraOdonto();
        }
        if (dados.endereco() != null){
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void adicionarClinica(Clinica clinica) {
        if (!this.clinicas.contains(clinica)) {
            this.clinicas.add(clinica);
        }
    }

}
