package br.com.fiap.challenge.Challenge01.dto;

public record EmailMessageDto(
    String emailTo,
    String title,
    String message,
    String messageType
) {
}
