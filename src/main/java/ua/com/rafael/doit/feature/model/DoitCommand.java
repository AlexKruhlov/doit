package ua.com.rafael.doit.feature.model;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import ua.com.rafael.doit.api.model.Command;
import ua.com.rafael.doit.api.model.Handler;

@RequiredArgsConstructor
public abstract class DoitCommand implements Command<String, String>, Handler<String> {
    private static final String COMMAND_NULL_ERROR = "Command should not be null or empty.";

    private final DoitCommand nextCommand;

    @Override
    public boolean handle(final String command) {
        Validate.notBlank(command, COMMAND_NULL_ERROR);
        return true;
    }

    @Override
    public String execute(final String command) {
        return nextCommand.execute(command);
    }
}
