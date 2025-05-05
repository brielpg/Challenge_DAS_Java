package br.com.fiap.challenge.email.services;

import br.com.fiap.challenge.email.dtos.ConsumeDto;
import br.com.fiap.challenge.email.models.Email;
import br.com.fiap.challenge.email.enums.StatusEmail;
import br.com.fiap.challenge.email.repositories.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public void sendEmail(ConsumeDto data){
        log.info("Iniciando processo de envio de email");
        var tempoInicial = System.currentTimeMillis();
        var email = new Email(data, emailFrom);

        try {
            var message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(data.emailTo());
            message.setSubject(data.title());
            message.setText(data.message());
            javaMailSender.send(message);

            email.setStatusEmail(StatusEmail.SENT);
            log.info("Email enviado com sucesso para: " + data.emailTo());

        } catch (MailException e){
            email.setStatusEmail(StatusEmail.ERROR);
            log.error("Erro ao enviar email: " + e.getMessage());

        } finally {
            log.info("Salvando email no banco de dados");
            emailRepository.save(email);
            log.info("Email salvo no banco de dados com status: " + email.getStatusEmail());

            this.calcularPerformance(tempoInicial);
        }
    }

    private void calcularPerformance(long tempoInicial){
        var tempoFinalizado = System.currentTimeMillis() - tempoInicial;
        log.info("Tempo levado para enviar o email: " + tempoFinalizado + " milissegundos");
    }
}
