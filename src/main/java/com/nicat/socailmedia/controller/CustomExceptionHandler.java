package com.nicat.socailmedia.controller;

import com.nicat.socailmedia.exception.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.MimeType;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleException(DomainNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodNullArgumentException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodNullArgumentException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleException(AuthenticationException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainExistException.class)
    public ResponseEntity<Map<String, String>> handleException(DomainExistException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleException(TokenNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            TypeMismatchException.class
    })
    public ResponseEntity<Map<String, String>> handleException(TypeMismatchException e) {
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            BindException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<Map<String, Object>> handleException(BindException e) {

        List<String> errors = new ArrayList<>();
        e.getFieldErrors()
                .forEach(err -> errors.add(err.getField() + ": " + err.getDefaultMessage()));
        e.getGlobalErrors()
                .forEach(err -> errors.add(err.getObjectName() + ": " + err.getDefaultMessage()));

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", errors);

        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleException(
            HttpMediaTypeNotSupportedException e) {

        String provided = Objects.requireNonNull(e.getContentType()).toString();
        List<String> supported = e.getSupportedMediaTypes().stream()
                .map(MimeType::toString)
                .collect(Collectors.toList());

        String error = provided + " is not one of the supported media types (" +
                String.join(", ", supported) + ")";

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleException(
            HttpMessageNotReadableException e) throws IOException {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleException(
            HttpRequestMethodNotSupportedException e) throws IOException {
        Map<String, String> errorResponse = new HashMap<>();
        String provided = e.getMethod();
        List<String> supported = Arrays.asList(Objects.requireNonNull(e.getSupportedMethods()));

        String error = provided + " is not one of the supported Http Methods (" +
                String.join(", ", supported) + ")";
        errorResponse.put("error", error);
        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.METHOD_NOT_ALLOWED.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(
            Exception e) throws IOException {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getLocalizedMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
