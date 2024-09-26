package br.com.fiap.challenge.Challenge01.repositories;

import br.com.fiap.challenge.Challenge01.models.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {

    boolean existsByCnpj(String cnpj);

    Clinica getReferenceByCnpj(String cnpj);

    Clinica findByCnpj(String cnpj);
}
