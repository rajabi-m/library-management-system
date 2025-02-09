package org.example.model;

import java.time.LocalDate;

public abstract class Asset {
    protected String title;


    public String getTitle() {
        return title;
    }

    public abstract String display();

    public abstract String toCsv();
}
