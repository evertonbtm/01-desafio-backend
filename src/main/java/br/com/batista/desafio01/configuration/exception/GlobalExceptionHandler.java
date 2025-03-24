package br.com.batista.desafio01.configuration.exception;


import br.com.batista.desafio01.exception.base.ApiInternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) throws JsonProcessingException {
        logger.error(ex.getMessage(), ex);
        StringBuilder message = new StringBuilder();
        ex.getConstraintViolations().forEach(violation ->
                message.append(violation.getMessage()).append("\n")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.toString());
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(org.springframework.web.bind.MethodArgumentNotValidException ex){
        logger.error(ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "validation error");
        response.put("message", "error");
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ApiInternalServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleApiInternalServerErrorException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        errors.put("ApiInternalServerError",ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "unexpected error");
        response.put("message", "error");
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        errors.put("error","Erro interno do servidor: " + ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "internal error");
        response.put("message", "error");
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        Map<String, Object> errors = new HashMap<>();
        errors.put("error",ex.getMessage());
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "unexpected error");
        response.put("message", "error");
        response.put("errors", errors);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
