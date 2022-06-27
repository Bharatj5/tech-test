package com.kraken.uk.exception;

import com.kraken.uk.dto.ErrorMessage;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiCommunicationException.class})
    protected ResponseEntity<ErrorMessage> handleServerErrors(final ApiCommunicationException ex) {
        final ErrorMessage serverSideError = new ErrorMessage(ex.getMessage());
        return new ResponseEntity<>(serverSideError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ErrorMessage> handleNotFound(final NotFoundException ex) {
        final ErrorMessage recordNotFound = new ErrorMessage(StringUtils.isNotBlank(ex.getMessage()) ? ex.getMessage() : "Record not found");
        return new ResponseEntity<>(recordNotFound, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {CallNotPermittedException.class})
    protected ResponseEntity<Object> handleCircuitBreakerErrors(final CallNotPermittedException ex) {
        final ErrorMessage errorMessage = new ErrorMessage("Third party services are not responding, please try again in some time.");
        return new ResponseEntity<>(errorMessage, HttpStatus.REQUEST_TIMEOUT);

    }
}