package ua.com.rafael.doit.api.view;

public interface DoitViewer<T> {

    T read();

    void write(T message);
}
