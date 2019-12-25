package ua.com.rafael.doit.feature.model;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.rafael.doit.api.model.Handler;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
abstract class DoitHandler implements Handler<CommandExecutingStatus, String[]> {
    private final Handler<CommandExecutingStatus, String[]> nextHandler;

    @Override
    public CommandExecutingStatus handle(String[] taskMessage) {
        return nextHandler.handle(taskMessage);
    }
}
