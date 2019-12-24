package ua.com.rafael.doit.api.model;

public interface Handler<S, T> {

    S handle(T taskMessage);
}
