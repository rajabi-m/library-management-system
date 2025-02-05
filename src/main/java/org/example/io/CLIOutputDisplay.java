package org.example.io;

public class CLIOutputDisplay implements OutputDisplay {
    @Override
    public void display(String text) {
        System.out.println(text);
    }
}
