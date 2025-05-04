package br.com.fiap.challenge.Challenge01.dto.paciente;

import br.com.fiap.challenge.Challenge01.dto.DtoEndereco;

import java.time.LocalDate;

public record DtoAtualizarPaciente(
        String nome,
        String telefone,
        String email,
        LocalDate dataNascimento,
        String nmrCarteiraOdonto,
        DtoEndereco endereco
) {
}
