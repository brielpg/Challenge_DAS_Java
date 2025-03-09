package br.com.fiap.challenge.Challenge01.dto.consulta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DtoAtualizarConsulta(
        String procedimento,
        String dentista,
        LocalDate dataConsulta,
        BigDecimal valorConsulta
) {
}
