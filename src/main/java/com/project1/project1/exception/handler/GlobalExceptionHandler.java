package com.project1.project1.exception.handler;

import com.project1.project1.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ErrorResponse build(String code, String message, List<String> details) {
        return new ErrorResponse(code, message, details);
    }

    // Handle invalid paths
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        var error = build(
                "PATH_NOT_FOUND",
                "Invalid path: " + ex.getRequestURL(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Handle DTO validation errors (e.g., @NotNull, @Size)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        var error = build(
                "VALIDATION_ERROR",
                "Request body validation failed",
                details
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Custom application exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResource(ResourceNotFoundException ex) {
        var error = build("RESOURCE_NOT_FOUND", ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<Object> handleInvalidOp(InvalidOperationException ex) {
        var error = build("INVALID_OPERATION", ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParamsNotValidException.class)
    public ResponseEntity<Object> handleParams(ParamsNotValidException ex) {
        var error = build("PARAMS_NOT_VALID", ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BodyNotValidException.class)
    public ResponseEntity<Object> handleBody(BodyNotValidException ex) {
        var error = build("BODY_NOT_VALID", ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Object> handleServer(ServerErrorException ex) {
        var error = build("SERVER_ERROR", ex.getMessage(), null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Fallback for all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleFallback(Exception ex) {
        // Safely get message or fallback to generic text
        String message = ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred.";

        var error = build(
                "UNEXPECTED_ERROR",
                message,
                Collections.singletonList(message) // avoids NullPointerException
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
