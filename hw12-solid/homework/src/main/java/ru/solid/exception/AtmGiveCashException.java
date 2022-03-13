package ru.solid.exception;

public class AtmGiveCashException extends RuntimeException {
    public AtmGiveCashException(String message) {
        super(message);
    }
}
