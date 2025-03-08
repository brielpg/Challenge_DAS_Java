package br.com.fiap.challenge.Challenge01.dto.relatorio;

import jakarta.validation.constraints.NotBlank;

public record DtoCriarRelatorio(
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    @NotBlank(message = "Descrição é obrigatório")
    String descricao,
    @NotBlank(message = "Imagem é obrigatório")
    String imagem
) {
}
