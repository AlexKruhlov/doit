package ua.com.rafael.doit.feature.model;

import ua.com.rafael.doit.feature.exceptions.IncorrectCommandDateException;
import ua.com.rafael.doit.feature.exceptions.IncorrectCommandStructureException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidatorCommand extends DoitCommand {
    private final static String CORRECT_TASK_PATTERN = "^doit [a-zA-Z]+( -[a-zA-Z]+)?( --[a-zA-Z]+(-[a-zA-Z]+)?)*( [a-zA-Z0-9]-[a-zA-Z0-9])?((?<=([a-zA-z0-9]-[a-zA-z0-9])),[a-zA-z0-9]-[a-zA-z0-9])*" +
            "(((?<!([a-zA-z0-9]-[a-zA-z0-9])) ?[a-zA-Z]*)|((?<!([a-zA-z0-9]-[a-zA-z0-9])) \".*\"))?" +
            "((( 2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))(-(0[0-9]|1[0-2]):[0-5][0-9]:[0-5][0-9])?)|" +
            "(( 2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))(-2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1])))|" +
            "((( 2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))(-(0[0-9]|1[0-2]):[0-5][0-9]:[0-5][0-9]))" +
            "((-2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))(-(0[0-9]|1[0-2]):[0-5][0-9]:[0-5][0-9]))))?";
    private final static String DATE_REGEX_PATTERN = "2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1])";
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public CommonValidatorCommand(DoitCommand nextCommand) {
        super(nextCommand);
    }

    @Override
    public String execute(final String command) {
        super.handle(command);
        validateCommandStructure(command);
        validateAllDatesCorrectness(command);
        return super.execute(command);
    }

    private void validateCommandStructure(final String command) {
        if (!command.matches(CORRECT_TASK_PATTERN)) {
            throw new IncorrectCommandStructureException(command);
        }
    }

    private void validateAllDatesCorrectness(final String command) {
        final Pattern pattern = Pattern.compile(DATE_REGEX_PATTERN);
        final Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            final String date = command.substring(matcher.start(), matcher.end());
            validateDateCorrectness(date);
        }
    }

    private void validateDateCorrectness(final String date) {
        LocalDate validDate = LocalDate.parse(date, DATE_FORMATTER);
        if (!date.equals(validDate.format(DATE_FORMATTER))) {
            throw new IncorrectCommandDateException(date);
        }
    }
}
