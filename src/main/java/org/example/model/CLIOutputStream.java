package org.example.model;

public class CLIOutputStream implements OutputDisplay {
    @Override
    public void display(String text) {
        System.out.println(text);
    }
}
