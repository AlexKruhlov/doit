package ua.com.rafael.doit.feature.exceptions;

import static java.lang.String.format;

public class IncorrectCommandDateException extends RuntimeException {
    private static final String INCORRECT_DATE_ERROR_FORMAT = "Date %s doesn't exist.";

    public IncorrectCommandDateException(final String date) {
        super(format(INCORRECT_DATE_ERROR_FORMAT, date));
    }
}
