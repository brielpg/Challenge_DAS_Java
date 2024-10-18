package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_das_clinicas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Clinica extends RepresentationModel<Clinica> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String razaoSocial;
    private LocalDate dataCadastro;
    @JsonIgnore
    private String senha;
    private String fotoClinica;
    @Embedded
    private Endereco endereco;
    @JsonIgnore
    @ManyToMany(mappedBy = "clinicas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Paciente> clientes = new ArrayList<>();

    public Clinica(DtoCriarClinica dados) {
        this.nome = dados.nome();
        this.cnpj = dados.cnpj();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.razaoSocial = dados.razaoSocial();
        this.dataCadastro = LocalDate.now();
        this.senha = dados.senha();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarClinica(DtoAtualizarClinica dados) {
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

    public void adicionarCliente(Paciente cliente) {
        if (!this.clientes.contains(cliente)) {
            this.clientes.add(cliente);
        }
    }
}
