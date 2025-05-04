package br.com.fiap.challenge.email.models;

import br.com.fiap.challenge.email.dtos.ConsumeDto;
import br.com.fiap.challenge.email.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_das_emails")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailFrom;
    private String emailTo;
    private String title;
    private String message;
    private LocalDateTime sendDateEmail;
    @Enumerated(EnumType.STRING)
    private StatusEmail statusEmail;

    public Email(ConsumeDto data){
        this.emailTo = data.emailTo();
        this.title = data.title();
        this.message = data.message();
    }
}
