package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.models.Paciente;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

public class DtoListarClinica extends RepresentationModel<DtoListarClinica>{
        public Long id;
        public String nome;
        public String cnpj;
        public String telefone;
        public String email;
        public String razaoSocial;
        public LocalDate dataCadastro;
        public String fotoClinica;
        public Endereco endereco;
        public List<Paciente> pacientes;

    public DtoListarClinica(Clinica clinica){
        this.id = clinica.getId();
        this.nome = clinica.getNome();
        this.cnpj = clinica.getCnpj();
        this.telefone = clinica.getTelefone();
        this.email = clinica.getEmail();
        this.razaoSocial = clinica.getRazaoSocial();
        this.dataCadastro = clinica.getDataCadastro();
        this.fotoClinica = clinica.getFotoClinica();
        this.endereco = clinica.getEndereco();
        this.pacientes = clinica.getPacientes();
    }
}
