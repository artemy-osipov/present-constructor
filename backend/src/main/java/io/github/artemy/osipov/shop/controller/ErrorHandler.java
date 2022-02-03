package io.github.artemy.osipov.shop.controller;

import io.github.artemy.osipov.shop.controller.dto.DError;
import io.github.artemy.osipov.shop.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public DError processEntityNotFound(EntityNotFoundException ex) {
        log.info("entity not found", ex);
        return new DError(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        log.info("invalid request", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> errors.put(
                error.getField(),
                error.getDefaultMessage()
        ));
        var response = new DError();
        response.setInternalMessage(ex.getMessage());
        response.setDetails(errors);

        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }
}
