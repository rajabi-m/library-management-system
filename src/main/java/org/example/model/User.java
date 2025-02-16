package org.example.model;

import org.example.model.observer.Subscriber;
import org.example.util.ANSICodes;

public class User implements Subscriber {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void notify(String message) {
        System.out.println(ANSICodes.GREEN + "Notification for " + username + ": " + ANSICodes.YELLOW + message + ANSICodes.RESET);
    }
}
