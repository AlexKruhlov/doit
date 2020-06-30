package ua.com.rafael.doit.feature.exceptions;

import static java.lang.String.format;

public class IncorrectCommandStructureException extends RuntimeException {
    private static final String COMMAND_INCORRECT_ERROR_FORMAT = "Incorrect command structure: %s";

    public IncorrectCommandStructureException(final String command) {
        super(format(COMMAND_INCORRECT_ERROR_FORMAT, command));
    }
}
