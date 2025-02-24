package org.example.exception;

public class InvalidAssetFileFormatException extends RuntimeException {
    public InvalidAssetFileFormatException(String message) {
        super(message);
    }
}
