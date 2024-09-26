package br.com.fiap.challenge.Challenge01.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosEndereco(
        @NotBlank(message = "Logradouro é obrigatório")
        String logradouro,
        @NotBlank(message = "Bairro é obrigatório")
        String bairro,
        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 00000-000")
        String cep,
        @NotBlank(message = "Cidade é obrigatório")
        String cidade,
        @NotBlank(message = "UF é obrigatório")
        @Size(min = 2, max = 2, message = "UF deve ter 2 caracteres")
        String uf,
        String complemento,
        @NotNull(message = "Número é obrigatório")
        Integer numero) {
}
