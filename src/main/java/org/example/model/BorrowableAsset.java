package org.example.model;

import org.example.model.observer.Subscribable;
import org.example.model.observer.Subscriber;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class BorrowableAsset extends Asset implements Subscribable {
    private AssetStatus status;
    private LocalDate returnDate = defaultReturnDate;
    private final ArrayList<Subscriber> subscribers = new ArrayList<>();

    public static final LocalDate defaultReturnDate = LocalDate.EPOCH;

    public BorrowableAsset(String id, String title, AssetStatus status, LocalDate returnDate) {
        super(id, title);
        this.status = status;
        this.returnDate = returnDate;
    }

    public BorrowableAsset(String title) {
        super(title);
        this.status = AssetStatus.Exist;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String message) {
        subscribers.forEach(subscriber -> subscriber.notify(message));
        subscribers.clear();
    }
}
