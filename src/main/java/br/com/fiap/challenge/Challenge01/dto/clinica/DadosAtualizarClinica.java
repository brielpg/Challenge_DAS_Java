package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.dto.DadosEndereco;
import br.com.fiap.challenge.Challenge01.models.Endereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizarClinica(
        @NotNull
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        String razaoSocial,
        String senha,
        String fotoClinica,
        DadosEndereco endereco
) {
}
