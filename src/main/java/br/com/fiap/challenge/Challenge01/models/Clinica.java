package br.com.fiap.challenge.Challenge01.models;

import br.com.fiap.challenge.Challenge01.dto.clinica.DtoAtualizarClinica;
import br.com.fiap.challenge.Challenge01.dto.clinica.DtoCriarClinica;
import br.com.fiap.challenge.Challenge01.enums.DasRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "t_das_clinicas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of = "id")
public class Clinica implements UserDetails {
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
    @Enumerated(EnumType.STRING)
    private DasRoles role;
    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
    @JsonIgnore
    @ManyToMany(mappedBy = "clinicas", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Paciente> pacientes = new ArrayList<>();

    public Clinica(DtoCriarClinica dados, String encryptedPassword) {
        this.nome = dados.nome();
        this.cnpj = dados.cnpj();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.razaoSocial = dados.razaoSocial();
        this.dataCadastro = LocalDate.now();
        this.senha = encryptedPassword;
        this.role = DasRoles.USER;
    }

    public void atualizarClinica(DtoAtualizarClinica dados, String encryptedPassword) {
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
        if (encryptedPassword != null) {
            this.senha = encryptedPassword;
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarEndereco(dados.endereco());
        }
    }

    public void adicionarPaciente(Paciente paciente) {
        if (!this.pacientes.contains(paciente)) {
            this.pacientes.add(paciente);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
