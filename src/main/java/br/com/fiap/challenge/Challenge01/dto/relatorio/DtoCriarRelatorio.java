package br.com.fiap.challenge.Challenge01.dto.relatorio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DtoCriarRelatorio(
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    @NotBlank(message = "Descrição é obrigatório")
    String descricao,
    @NotBlank(message = "Imagem é obrigatório")
    String imagem
) {
}
