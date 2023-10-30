package com.bbm.banking.exception.handler;

import com.bbm.banking.exception.BadRequestException;
import com.bbm.banking.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class BankExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle(ex.getMessage());
        errorResponse.setTime(OffsetDateTime.now());
        errorResponse.setPath(request.getRequestURI());

        return ResponseEntity.badRequest()
                .body(errorResponse);

        //return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle(ex.getMessage());
        errorResponse.setTime(OffsetDateTime.now());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, status);
    }
}
