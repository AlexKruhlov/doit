package ua.com.rafael.doit.api.model;

public interface Command<T> {

    void execute(T taskMessage);
}
