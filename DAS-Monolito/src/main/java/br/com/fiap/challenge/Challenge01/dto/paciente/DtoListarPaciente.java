package br.com.fiap.challenge.Challenge01.dto.paciente;


import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.models.Paciente;

import java.time.LocalDate;
import java.util.List;

public class DtoListarPaciente {
        public Long id;
        public String nome;
        public String cpf;
        public String telefone;
        public String email;
        public String nmrCarteiraOdonto;
        public LocalDate dataNascimento;
        public Integer qtdConsultas;
        public Endereco endereco;
        public List<Clinica> clinicas;

    public DtoListarPaciente(Paciente paciente){
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.cpf = paciente.getCpf();
        this.telefone = paciente.getTelefone();
        this.email = paciente.getEmail();
        this.nmrCarteiraOdonto = paciente.getNmrCarteiraOdonto();
        this.dataNascimento = paciente.getDataNascimento();
        this.qtdConsultas = paciente.getQtdConsultas();
        this.endereco = paciente.getEndereco();
        this.clinicas = paciente.getClinicas();
    }
}
