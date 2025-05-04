package br.com.fiap.challenge.email.consumers;

import br.com.fiap.challenge.email.dtos.ConsumeDto;
import br.com.fiap.challenge.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload ConsumeDto data){
        System.out.println("Email: " + data);
    }
}
