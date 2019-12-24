package ua.com.rafael.doit.api.controller;

public interface DoitController<S, T> {

    S doTask(T taskMessage);
}
