package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.dto.DtoEndereco;
import jakarta.validation.constraints.Email;

public record DtoAtualizarClinica(
        String nome,
        String telefone,
        @Email
        String email,
        String razaoSocial,
        String senha,
        DtoEndereco endereco
) {
}
