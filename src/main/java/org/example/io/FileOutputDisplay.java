package org.example.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileOutputDisplay implements OutputDisplay {
    private final String filePath;

    public FileOutputDisplay(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void display(String text) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
