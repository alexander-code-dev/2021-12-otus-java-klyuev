package ru.solid.exception;

public class AtmTakeCashException extends RuntimeException{
    public AtmTakeCashException(String message) {
        super(message);
    }
}
