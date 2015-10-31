package ru.home.shop.exception;

public class ConcurrentException extends RuntimeException {

    public ConcurrentException() {
    }

    public ConcurrentException(String message) {
        super(message);
    }
}
