package br.com.fiap.challenge.Challenge01.exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Date cannot be higher than the current one");
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
