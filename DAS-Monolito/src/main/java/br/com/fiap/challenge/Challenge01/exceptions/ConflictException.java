package br.com.fiap.challenge.Challenge01.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException() {
        super("Already exists");
    }

    public ConflictException(String message) {
        super(message);
    }
}
