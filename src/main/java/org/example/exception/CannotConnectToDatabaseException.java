package org.example.exception;

public class CannotConnectToDatabaseException extends RuntimeException {
    public CannotConnectToDatabaseException(String message) {
        super(message);
    }
}
