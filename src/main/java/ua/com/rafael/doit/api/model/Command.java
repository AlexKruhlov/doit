package ua.com.rafael.doit.api.model;

public interface Command<T, R> {

    R execute(T command);
}
