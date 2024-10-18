package br.com.fiap.challenge.Challenge01.dto.paciente;


import br.com.fiap.challenge.Challenge01.models.Paciente;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

public class DtoListarPaciente extends RepresentationModel<DtoListarPaciente> {
        public Long id;
        public String nome;
        public String cpf;
        public String telefone;
        public String nmrCarteiraOdonto;
        public LocalDate dataNascimento;
        public Integer qtdConsultas;
        public String fotoCliente;
        public Endereco endereco;
        public List<Clinica> clinicas;

    public DtoListarPaciente(Paciente cliente){
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.nmrCarteiraOdonto = cliente.getNmrCarteiraOdonto();
        this.dataNascimento = cliente.getDataNascimento();
        this.qtdConsultas = cliente.getQtdConsultas();
        this.fotoCliente = cliente.getFotoCliente();
        this.endereco = cliente.getEndereco();
        this.clinicas = cliente.getClinicas();
    }
}
