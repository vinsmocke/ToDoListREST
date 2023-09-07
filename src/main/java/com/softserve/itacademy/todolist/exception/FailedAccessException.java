package com.softserve.itacademy.todolist.exception;

public class FailedAccessException extends RuntimeException {
    public FailedAccessException() {
    }

    public FailedAccessException(String message) {
        super(message);
    }
}