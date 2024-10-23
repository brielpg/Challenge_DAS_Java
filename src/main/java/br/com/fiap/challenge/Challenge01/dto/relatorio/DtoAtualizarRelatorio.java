package br.com.fiap.challenge.Challenge01.dto.relatorio;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DtoAtualizarRelatorio(
        @NotNull(message = "ID é obrigatório")
        Long id,
        String titulo,
        String descricao,
        String dentista,
        String imagem
) {
}
