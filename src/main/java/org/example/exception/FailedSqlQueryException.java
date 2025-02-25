package org.example.exception;

public class FailedSqlQueryException extends RuntimeException {
    public FailedSqlQueryException(String message) {
        super(message);
    }
}
