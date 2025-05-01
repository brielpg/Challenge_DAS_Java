package br.com.fiap.challenge.Challenge01.dto.consulta;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoCriarRelatorio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DtoCriarConsulta (
        @NotBlank(message = "Procedimento é obrigatório")
        String procedimento,
        @NotBlank(message = "Dentista é obrigatório")
        String dentista,
        @NotNull(message = "Data da Consulta é obrigatória")
        LocalDate dataConsulta,
        @NotNull(message = "Valor da Consulta é obrigatório")
        BigDecimal valorConsulta,
        @NotNull(message = "Clinica é obrigatório")
        Long clinica_id,
        @NotBlank(message = "Paciente é obrigatório")
        String paciente_cpf,
        @NotNull(message = "Relatório é obrigatório")
        @Valid
        DtoCriarRelatorio relatorio
){
}
