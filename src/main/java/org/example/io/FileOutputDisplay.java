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
            System.out.println("An error occurred while writing to the output file.");
            throw new RuntimeException(e);
        }
    }
}
