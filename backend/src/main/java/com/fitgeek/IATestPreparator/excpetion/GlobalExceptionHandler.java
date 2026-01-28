package com.fitgeek.IATestPreparator.excpetion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        String msg = e.getMessage();
        if (msg.contains("Invalid credentials")) {
            return ResponseEntity.status(401).body(msg);
        } else if (msg.contains("already exists")) {
            return ResponseEntity.status(409).body(msg);
        } else {
            return ResponseEntity.badRequest().body(msg);
        }
    }

}
