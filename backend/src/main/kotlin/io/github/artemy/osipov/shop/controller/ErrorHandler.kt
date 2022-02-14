package io.github.artemy.osipov.shop.controller

import io.github.artemy.osipov.shop.controller.dto.DError
import io.github.artemy.osipov.shop.exception.EntityNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun processEntityNotFound(ex: EntityNotFoundException): DError {
        logger.info("entity not found", ex)
        return DError(internalMessage = ex.message ?: "")
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.info("invalid request", ex)
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.fieldErrors.forEach { error: FieldError ->
            errors[error.field] = error.defaultMessage ?: ""
        }
        val response = DError(
            internalMessage = ex.message ?: "",
            details = errors
        )
        return handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request)
    }
}