package ua.com.rafael.doit.api.model;

public interface Handler<T> {

    boolean handle(T taskMessage);
}
