package ua.com.rafael.doit;

import ua.com.rafael.doit.feature.model.DoitCommand;

public class CommandMock extends DoitCommand {

    public CommandMock(DoitCommand nextCommand) {
        super(nextCommand);
    }

    @Override
    public boolean handle(final String command) {
        return true;
    }

    @Override
    public String execute(final String command) {
        return command;
    }
}
