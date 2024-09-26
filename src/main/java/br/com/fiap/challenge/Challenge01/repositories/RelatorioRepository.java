package br.com.fiap.challenge.Challenge01.repositories;

import br.com.fiap.challenge.Challenge01.models.Relatorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    Page<Relatorio> findByClinica_Id(Long clinica_id, Pageable paginacao);

    Page<Relatorio> findByCliente_Id(Long cliente_id, Pageable paginacao);
}
