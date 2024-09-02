package com.example.banking.advice;

import com.example.banking.model.exception.ValidationError;
import com.example.banking.model.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseStatus
    public ResponseEntity<Object> handleCustomException(CustomException exception) {
        String userMessage = exception.getCode().getDescription();
        log.error(userMessage, exception);
        return new ResponseEntity<>(userMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus
    public ResponseEntity<Object> handleAnyOtherException(Exception exception) {
        String userMessage = CustomException.ErrorCode.GENERAL.getDescription();
        log.error(userMessage, exception);
        return new ResponseEntity<>(userMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode httpStatusCode, WebRequest request) {
        ValidationError validationError = new ValidationError(request.getDescription(false),
                "Invalid Request Data, Your request is either missing required data or contains invalid values");
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        fieldErrors.forEach(fieldError -> validationError.addError(fieldError.getField(), fieldError.getDefaultMessage()));
        globalErrors.forEach(globalError -> validationError.addError(globalError.getObjectName(), globalError.getDefaultMessage()));
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }
}
