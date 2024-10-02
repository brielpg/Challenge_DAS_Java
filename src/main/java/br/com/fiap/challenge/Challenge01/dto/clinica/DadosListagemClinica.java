package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.models.ClienteDaClinica;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import br.com.fiap.challenge.Challenge01.models.Relatorio;

import java.time.LocalDate;
import java.util.List;

public record DadosListagemClinica(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        String razaoSocial,
        LocalDate dataCadastro,
        String fotoClinica,
        Endereco endereco,
        List<ClienteDaClinica> clientes
) {
    public DadosListagemClinica(Clinica clinica){
        this(clinica.getId(), clinica.getNome(), clinica.getCnpj(), clinica.getTelefone(), clinica.getEmail(), clinica.getRazaoSocial(), clinica.getDataCadastro(), clinica.getFotoClinica(), clinica.getEndereco(), clinica.getClientes());
    }
}
