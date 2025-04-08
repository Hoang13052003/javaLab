package com.example.Observer.base;

import java.util.ArrayList;
import java.util.List;

public class Subjects {
    private final List<Observer> observers = new ArrayList<>();

    public void attachObservers(Observer observer) {
        this.observers.add(observer);
    }

    public void detachObservers(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(Subjects subject, Object arg) {
        this.observers.forEach((observer) -> observer.notify(subject, arg));
    }

}
