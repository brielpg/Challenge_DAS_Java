package br.com.fiap.challenge.Challenge01.repositories;

import br.com.fiap.challenge.Challenge01.models.Clinica;
import br.com.fiap.challenge.Challenge01.models.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByCpf(String cpf);

    Paciente findByCpf(String cpf);

    Paciente getReferenceByCpf(String cpf);

    List<Paciente> findByClinicasContaining(Clinica clinica);
}
