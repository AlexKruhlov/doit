package ua.com.rafael.doit.feature.model;

import ua.com.rafael.doit.api.model.Command;
import ua.com.rafael.doit.api.model.Handler;

public class DoitCommand extends DoitHandler implements Command<String[]> {

    public DoitCommand(Handler<CommandExecutingStatus, String[]> nextHandler) {
        super(nextHandler);
    }

    @Override
    public CommandExecutingStatus handle(String[] taskMessage) {
        return null;
    }

    @Override
    public void execute(String[] taskMessage) {
    }
}
