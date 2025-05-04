package br.com.fiap.challenge.Challenge01.services;

import br.com.fiap.challenge.Challenge01.dto.EmailMessageDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void sendEmailMessage(EmailMessageDto emailMessageDto) {
        rabbitTemplate.convertAndSend("", routingKey, emailMessageDto);
    }
}
