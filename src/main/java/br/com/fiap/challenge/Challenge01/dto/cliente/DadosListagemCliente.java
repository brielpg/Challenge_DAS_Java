package br.com.fiap.challenge.Challenge01.dto.cliente;


import br.com.fiap.challenge.Challenge01.models.ClienteDaClinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;

import java.time.LocalDate;

public record DadosListagemCliente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String nmrCarteiraOdonto,
        String dataNascimento,
        Integer qtdConsultas,
        String fotoCliente,
        Endereco endereco
) {
    public DadosListagemCliente(ClienteDaClinica cliente){
        this(cliente.getId(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getNmrCarteiraOdonto(), cliente.getDataNascimento(), cliente.getQtdConsultas(), cliente.getFotoCliente(), cliente.getEndereco());
    }
}
