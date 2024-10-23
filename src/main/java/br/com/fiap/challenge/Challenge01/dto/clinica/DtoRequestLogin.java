package br.com.fiap.challenge.Challenge01.dto.clinica;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DtoRequestLogin(
        @NotBlank
        @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{4}-\\d{2}$", message = "CNPJ inválido. O formato deve ser 99.999.999-9999-99")
        String cnpj,
        @NotBlank
        String senha
) {
}
