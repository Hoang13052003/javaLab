package com.example.Observer.base;

public abstract class Observer {
    protected Subjects subjects;

    public abstract void notify(Subjects subjects, Object data);
}
