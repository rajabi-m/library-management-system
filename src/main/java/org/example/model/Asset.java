package org.example.model;

import java.util.Objects;
import java.util.UUID;

public abstract class Asset {
    protected final String type;
    private String title;
    private String id;

    public Asset(String title){
        type = this.getClass().getSimpleName();
        this.id = UUID.randomUUID().toString();
        this.title = title;
    }

    public Asset(String id, String title){
        type = this.getClass().getSimpleName();
        this.id = id;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public abstract void update(Asset asset);

    public abstract String display();

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Asset asset)) return false;
        return Objects.equals(title, asset.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }
}
