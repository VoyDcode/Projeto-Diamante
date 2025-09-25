package com.fiap.bank_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("erro", "Dados inválidos");
        body.put("detalhes", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleBusiness(NegocioException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("erro", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }
}
