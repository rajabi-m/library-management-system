package org.example.exception;

public class ConfigFileNotFoundException extends RuntimeException {
    private final String filePath;

    public ConfigFileNotFoundException(String filePath) {
        super();
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }
}
