package br.com.fiap.challenge.Challenge01.dto.paciente;

import br.com.fiap.challenge.Challenge01.dto.DtoEndereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record DtoAtualizarPaciente(
        String nome,
        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF inválido, deve conter 11 dígitos")
        String cpf,
        String telefone,
        LocalDate dataNascimento,
        String nmrCarteiraOdonto,
        String fotoPaciente,
        DtoEndereco endereco
) {
}
