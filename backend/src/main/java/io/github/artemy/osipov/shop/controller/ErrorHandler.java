package io.github.artemy.osipov.shop.controller;

import io.github.artemy.osipov.shop.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Void> processEntityNotFound(EntityNotFoundException ex) {
        log.info("Entity - {} with id {} not found", ex.getTargetClass(), ex.getId());
        return ResponseEntity.notFound().build();
    }
}
