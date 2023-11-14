package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.controller.dto.DError
import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ErrorHandler() {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFound(ex: EntityNotFoundException): DError {
        logger.info(ex) { "entity not found" }
        return DError(internalMessage = ex.message ?: "")
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): DError {
        logger.info(ex) { "invalid request" }
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.fieldErrors.forEach { error: FieldError ->
            errors[error.field] = error.defaultMessage ?: ""
        }
        return DError(
            internalMessage = ex.message ?: "",
            details = errors
        )
    }
}