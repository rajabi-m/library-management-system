package org.example.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger logger = LogManager.getLogger("Global Exception Handler");

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        switch (throwable) {
            case InvalidAssetFileFormatException e -> {
                logger.error("Invalid asset file format: {}", e.getMessage());
            }
            case ConfigFileNotFoundException e -> {
                logger.error("Config file not found at {}", e.getFilePath());
            }
            case InvalidConfigFileFormatException e -> {
                logger.error("Invalid config file format");
            }
            default -> logger.error("unhandled system exception happened", throwable);
        }
    }
}
