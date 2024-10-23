package br.com.fiap.challenge.Challenge01.dto.consulta;

import br.com.fiap.challenge.Challenge01.dto.relatorio.DtoAtualizarRelatorio;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DtoAtualizarConsulta(
        @NotNull(message = "ID é obrigatório")
        Long id,
        String procedimento,
        String dentista,
        LocalDate dataConsulta,
        BigDecimal valorConsulta
) {
}
