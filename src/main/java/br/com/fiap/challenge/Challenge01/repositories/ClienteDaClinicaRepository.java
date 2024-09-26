package br.com.fiap.challenge.Challenge01.repositories;

import br.com.fiap.challenge.Challenge01.models.ClienteDaClinica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDaClinicaRepository extends JpaRepository<ClienteDaClinica, Long> {
    boolean existsByCpf(String cpf);

    ClienteDaClinica findByCpf(String cpf);

    ClienteDaClinica getReferenceByCpf(String cpf);
}
