package br.com.fiap.challenge.Challenge01.repositories;

import br.com.fiap.challenge.Challenge01.models.Clinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface ClinicaRepository extends JpaRepository<Clinica, Long> {

    boolean existsByCnpj(String cnpj);

    Clinica findByCnpj(String cnpj);

    Optional<UserDetails> findByEmail(String email);
}
