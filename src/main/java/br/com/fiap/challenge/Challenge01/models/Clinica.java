package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.clinica.DadosAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DadosCriarClinica;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_das_clinicas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Clinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String razaoSocial;
    private String senha;
    private String fotoClinica;
    @Embedded
    private Endereco endereco;
    @ManyToMany(mappedBy = "clinicas", cascade = CascadeType.ALL)
    private List<ClienteDaClinica> clientes;
    @OneToMany(mappedBy = "clinica", cascade = CascadeType.ALL)
    private List<Relatorio> relatorios;

    public Clinica(DadosCriarClinica dados) {
        this.nome = dados.nome();
        this.cnpj = dados.cnpj();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.razaoSocial = dados.razaoSocial();
        this.senha = dados.senha();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarClinica(DadosAtualizarClinica dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.email() != null) {
            this.email = dados.email();
        }
        if (dados.razaoSocial() != null) {
            this.razaoSocial = dados.razaoSocial();
        }
        if (dados.senha() != null) {
            this.senha = dados.senha();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }
}
