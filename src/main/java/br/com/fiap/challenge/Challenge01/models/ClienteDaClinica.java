package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.cliente.DadosAtualizarCliente;
import br.com.fiap.challenge.Challenge01.dto.cliente.DadosCriarCliente;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_das_clientes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class ClienteDaClinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String nmrCarteiraOdonto;
    private String dataNascimento;
    private Integer qtdConsultas;
    private String fotoCliente;
    @Embedded
    private Endereco endereco;
    @ManyToMany
    @JoinTable(name = "cliente_clinica", joinColumns = @JoinColumn(name = "cliente_id"), inverseJoinColumns = @JoinColumn(name = "clinica_id"))
    private List<Clinica> clinicas;
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Relatorio> relatorios;

    public ClienteDaClinica(DadosCriarCliente dados) {
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.dataNascimento = dados.dataNascimento();
        this.nmrCarteiraOdonto = dados.nmrCarteiraOdonto();
        this.fotoCliente = dados.fotoCliente();
        this.qtdConsultas = 0;
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarCliente(DadosAtualizarCliente dados) {
        if (dados.nome() != null){
            this.nome = dados.nome();
        }
        if (dados.cpf() != null){
            this.cpf = dados.cpf();
        }
        if (dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.dataNascimento() != null){
            this.dataNascimento = dados.dataNascimento();
        }
        if (dados.nmrCarteiraOdonto() != null){
            this.nmrCarteiraOdonto = dados.nmrCarteiraOdonto();
        }
        if (dados.fotoCliente() != null){
            this.fotoCliente = dados.fotoCliente();
        }
        if (dados.endereco() != null){
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

}
