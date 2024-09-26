package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Endereco;

public record DadosListagemClinica(
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        String razaoSocial,
        String senha,
        String fotoClinica,
        Endereco endereco
) {
    public DadosListagemClinica(Clinica clinica){
        this(clinica.getId(), clinica.getNome(), clinica.getCnpj(), clinica.getTelefone(), clinica.getEmail(), clinica.getRazaoSocial(), clinica.getSenha(), clinica.getFotoClinica(), clinica.getEndereco());
    }
}
