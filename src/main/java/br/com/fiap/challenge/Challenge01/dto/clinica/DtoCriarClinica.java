package br.com.fiap.challenge.Challenge01.dto.clinica;

import br.com.fiap.challenge.Challenge01.dto.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DtoCriarClinica(
        @NotBlank(message = "Nome da clínica é obrigatório")
        String nome,

        @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ inválido. O formato deve ser 99.999.999/9999-99")
        @NotBlank(message = "CNPJ é obrigatório")
        String cnpj,

        @NotNull(message = "Telefone é obrigatório")
        String telefone,

        @Email(message = "Email inválido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotBlank(message = "Razão social é obrigatória")
        String razaoSocial,

        @NotNull(message = "Senha é obrigatória")
        String senha,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DadosEndereco endereco
) {
}
