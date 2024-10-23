package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.dto.DtoEndereco;
import jakarta.validation.constraints.NotNull;

public record DtoAtualizarClinica(
        @NotNull
        Long id,
        String nome,
        String cnpj,
        String telefone,
        String email,
        String razaoSocial,
        String senha,
        String fotoClinica,
        DtoEndereco endereco
) {
}
