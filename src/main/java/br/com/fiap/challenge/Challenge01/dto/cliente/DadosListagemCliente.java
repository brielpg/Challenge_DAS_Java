package br.com.fiap.challenge.Challenge01.dto.cliente;


import br.com.fiap.challenge.Challenge01.models.ClienteDaClinica;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.models.Relatorio;

import java.time.LocalDate;
import java.util.List;

public record DadosListagemCliente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String nmrCarteiraOdonto,
        LocalDate dataNascimento,
        Integer qtdConsultas,
        String fotoCliente,
        Endereco endereco,
        List<Clinica> clinicas
) {
    public DadosListagemCliente(ClienteDaClinica cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getNmrCarteiraOdonto(), cliente.getDataNascimento(), cliente.getQtdConsultas(), cliente.getFotoCliente(), cliente.getEndereco(), cliente.getClinicas());
    }
}
