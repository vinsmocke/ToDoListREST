package com.softserve.itacademy.todolist.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler { // consider extending ResponseEntityExceptionHandler

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNullEntityReferenceException(NullEntityReferenceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleFailedAccessException(FailedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException ex) {
        return ResponseEntity.badRequest().body(ex.toString());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
