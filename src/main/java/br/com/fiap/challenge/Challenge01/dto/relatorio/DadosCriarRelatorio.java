package br.com.fiap.challenge.Challenge01.dto.relatorio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCriarRelatorio(
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    @NotBlank(message = "Descrição é obrigatório")
    String descricao,
    @NotBlank(message = "Imagem é obrigatório")
    String imagem,
    @NotNull(message = "Data da Consulta é obrigatória")
    String dataConsulta,
    @NotNull(message = "Valor da Consulta é obrigatória")
    BigDecimal valorConsulta,
    @NotBlank(message = "Médico/Profissional é obrigatório")
    String medico,
    @NotNull(message = "ID da Clínica é obrigatória")
    Long clinica_id,
    @NotNull(message = "CPF do Paciente é obrigatória")
    String cliente_cpf
) {
}
