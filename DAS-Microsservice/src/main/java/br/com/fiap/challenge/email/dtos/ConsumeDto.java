package br.com.fiap.challenge.email.dtos;

public record ConsumeDto(
        String emailTo,
        String title,
        String message,
        String messageType
) {
}
