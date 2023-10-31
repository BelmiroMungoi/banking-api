package com.bbm.banking.exception.handler;

import com.bbm.banking.exception.BadRequestException;
import com.bbm.banking.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class BankExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<ValidationError> validationErrors = new ArrayList<>();

        for (ObjectError objectError: ((MethodArgumentNotValidException) ex)
                .getBindingResult().getAllErrors()) {
            String name = ((FieldError) objectError).getField();
            String msg = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
            validationErrors.add(new ValidationError(name, msg));
        }

        StandardErrorResponse errorResponse = new StandardErrorResponse();
        errorResponse.setCode(status.value());
        errorResponse.setStatus(status);
        errorResponse.setTitle("Erro de validacão! Um ou mais campos estão inválidos!");
        errorResponse.setTime(OffsetDateTime.now());
        errorResponse.setPath(request.getContextPath());
        errorResponse.setFields(validationErrors);

        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

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
