package ru.home.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public ValidationException(String key, String message) {
        errors = new HashMap<>();
        errors.put(key, message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
