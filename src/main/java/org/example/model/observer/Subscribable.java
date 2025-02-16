package org.example.model.observer;

public interface Subscribable {
    void subscribe(Subscriber subscriber);

    void unsubscribe(Subscriber subscriber);

    void notifySubscribers(String message);
}
