package br.com.fiap.challenge.Challenge01.repositories;

import br.com.fiap.challenge.Challenge01.models.Relatorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {
    Page<Relatorio> findByClinicaId(Long idCliente, Pageable paginacao);

    Page<Relatorio> findByClienteId(Long idCliente, Pageable paginacao);
}
