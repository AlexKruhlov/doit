package ua.com.rafael.doit.feature.model;

import lombok.RequiredArgsConstructor;
import ua.com.rafael.doit.api.model.Handler;

@RequiredArgsConstructor
abstract class DoitHandler implements Handler<CommandExecutingStatus, String> {
    private final Handler<CommandExecutingStatus, String> nextHandler;

    @Override
    public CommandExecutingStatus handle(String taskMessage) {
        return nextHandler.handle(taskMessage);
    }
}
