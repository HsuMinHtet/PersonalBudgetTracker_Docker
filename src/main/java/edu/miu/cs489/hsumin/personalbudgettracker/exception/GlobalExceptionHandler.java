package edu.miu.cs489.hsumin.personalbudgettracker.exception;


import edu.miu.cs489.hsumin.personalbudgettracker.exception.accountHolder.UserNotFoundException;
import edu.miu.cs489.hsumin.personalbudgettracker.exception.category.UnableToDeleteCategory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String,String > errors= new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    String errorMessage= fieldError.getDefaultMessage();
                    String fieldName=fieldError.getField();
                    errors.put(fieldName, errorMessage);
                }
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleInternalServerError(RuntimeException ex, WebRequest request) {
        String errorMessage = "An unexpected error occurred. << "+ ex.getMessage()+">>";
        System.err.println("Internal Server Error: " + ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        String errorMessage = ex.getReason() != null ? ex.getReason() : "An unexpected error occurred.";
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }
}
