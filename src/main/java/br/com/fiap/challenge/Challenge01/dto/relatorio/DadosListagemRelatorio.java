package br.com.fiap.challenge.Challenge01.dto.relatorio;

import br.com.fiap.challenge.Challenge01.models.ClienteDaClinica;
import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Relatorio;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosListagemRelatorio(
        Long id,
        String titulo,
        String descricao,
        String status,
        String medico,
        String dataConsulta,
        LocalDate dataEnvioRelatorio,
        BigDecimal valorConsulta,
        String imagem,
        Clinica clinica,
        ClienteDaClinica cliente
) {

    public DadosListagemRelatorio(Relatorio relatorio){
        this(relatorio.getId(), relatorio.getTitulo(), relatorio.getDescricao(), relatorio.getStatus(), relatorio.getMedico(), relatorio.getDataConsulta(), relatorio.getDataEnvioRelatorio(), relatorio.getValorConsulta(), relatorio.getImagem(), relatorio.getClinica(), relatorio.getCliente());
    }
}
