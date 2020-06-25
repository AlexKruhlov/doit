package ua.com.rafael.doit.feature.model;

import ua.com.rafael.doit.api.model.Command;
import ua.com.rafael.doit.api.model.Handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidatorCommand extends DoitHandler implements Command<String, String> {

    public CommonValidatorCommand(Handler<CommandExecutingStatus, String> nextHandler) {
        super(nextHandler);
    }

    @Override
    public CommandExecutingStatus handle(final String taskMessage) {
        return null;
    }

    @Override
    public String execute(final String taskMessage) {
        final String regex = "^doit [a-zA-Z]+( -[a-zA-Z]+)?( --[a-zA-Z]+(-[a-zA-Z]+)?)*";
        if (taskMessage.matches(regex)){
            return taskMessage;
        }
        throw new RuntimeException("Do not valid");
    }
}
