package com.intern.CoponAPI.exception;

public class ConflictException extends RuntimeException{
    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message);
    }
}
