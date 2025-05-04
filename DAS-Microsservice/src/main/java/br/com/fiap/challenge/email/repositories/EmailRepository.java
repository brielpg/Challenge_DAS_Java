package br.com.fiap.challenge.email.repositories;

import br.com.fiap.challenge.email.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
