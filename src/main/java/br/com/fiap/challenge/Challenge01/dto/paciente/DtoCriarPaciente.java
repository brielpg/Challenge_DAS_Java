package br.com.fiap.challenge.Challenge01.dto.paciente;

import br.com.fiap.challenge.Challenge01.dto.DtoEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DtoCriarPaciente(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF inválido, deve conter 11 dígitos")
        String cpf,

        @NotBlank(message = "Telefone é obrigatório")
        String telefone,

        @NotNull(message = "Data de nascimento é obrigatória")
        LocalDate dataNascimento,

        @NotBlank(message = "Número da Carteirinha Odonto é obrigatória")
        String nmrCarteiraOdonto,

        @NotNull(message = "Foto do Paciente é obrigatória")
        String fotoPaciente,

        @NotNull(message = "Endereço é obrigatório")
        @Valid
        DtoEndereco endereco
) {
}
