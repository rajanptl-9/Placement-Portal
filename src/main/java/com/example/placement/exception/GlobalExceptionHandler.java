package com.example.placement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonParseException(HttpMessageNotReadableException ex) {
        String rootCause = getRootMessage(ex);
        System.err.println("Root Cause (JSON Error): " + rootCause);

        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("type", "Invalid JSON Format");
        error.put("cause", rootCause);
        error.put("tip", "Ensure you send a valid JSON object {...} and not a plain string.");

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String rootCause = getRootMessage(ex);
        System.err.println("Root Cause (Media Error): " + rootCause);

        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("type", "Unsupported Media Type");
        error.put("cause", rootCause);
        error.put("tip", "Set header 'Content-Type: application/json' in your request.");

        return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        String rootCause = getRootMessage(ex);
        System.err.println("Root Cause (Server Error): " + rootCause);

        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("type", "Internal Server Error");
        error.put("cause", rootCause);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getRootMessage(Throwable throwable) {
        Throwable root = throwable;
        while (root.getCause() != null && root.getCause() != root) {
            root = root.getCause();
        }
        return root.getMessage() != null ? root.getMessage() : root.getClass().getSimpleName();
    }
}
