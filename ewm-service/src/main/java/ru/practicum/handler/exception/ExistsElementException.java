package ru.practicum.handler.exception;

public class ExistsElementException extends RuntimeException {
    public ExistsElementException(String error) {
        super(error);
    }
}
