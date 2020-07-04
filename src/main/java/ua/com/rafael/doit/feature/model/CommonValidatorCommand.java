package ua.com.rafael.doit.feature.model;

import ua.com.rafael.doit.feature.exceptions.IncorrectCommandDateException;
import ua.com.rafael.doit.feature.exceptions.IncorrectCommandDateTimePeriodException;
import ua.com.rafael.doit.feature.exceptions.IncorrectCommandStructureException;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private final static String DATE_REGEX_PATTERN = "(2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))";
    private final static String DATE_TIME_REGEX_PATTERN = "(2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))(-(0[0-9]|1[0-2]):[0-5][0-9]:[0-5][0-9])";

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss");

    private final static String DEFAULT_TIME_SUFFIX = "-00:00:00";

    private final static String DATE_PERIOD_SEPARATOR = "-";

    private final static String DATE_PERIOD_REGEX_PATTERN = DATE_REGEX_PATTERN + DATE_PERIOD_SEPARATOR
            + DATE_REGEX_PATTERN;
    private final static String DATE_TIME_PERIOD_REGEX_PATTERN = DATE_TIME_REGEX_PATTERN + DATE_PERIOD_SEPARATOR
            + DATE_TIME_REGEX_PATTERN;

    private final static int START_DATE_INDEX = 0;
    private final static int END_DATE_INDEX = 1;


    public CommonValidatorCommand(DoitCommand nextCommand) {
        super(nextCommand);
    }

    @Override
    public String execute(final String command) {
        super.handle(command);
        validateCommandStructure(command);
        validateAllDates(command);
        validatePeriod(command);
        return super.execute(command);
    }

    private void validateCommandStructure(final String command) {
        if (!command.matches(CORRECT_TASK_PATTERN)) {
            throw new IncorrectCommandStructureException(command);
        }
    }

    private void validateAllDates(final String command) {
        final Pattern pattern = Pattern.compile(DATE_REGEX_PATTERN);
        final Matcher matcher = pattern.matcher(command);
        while (matcher.find()) {
            final String date = command.substring(matcher.start(), matcher.end());
            validateDate(date);
        }
    }

    private void validateDate(final String date) {
        LocalDate validDate = LocalDate.parse(date, DATE_FORMATTER);
        if (!date.equals(validDate.format(DATE_FORMATTER))) {
            throw new IncorrectCommandDateException(date);
        }
    }

    private void validatePeriod(final String command) {
        if (command.matches(DATE_TIME_PERIOD_REGEX_PATTERN)) {
            validatePeriod(command, DATE_TIME_PERIOD_REGEX_PATTERN);
        } else {
            validatePeriod(command, DATE_PERIOD_REGEX_PATTERN);
        }
    }

    private void validatePeriod(final String command,
                                final String periodPattern) {
        final Pattern pattern = Pattern.compile(periodPattern);
        final Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            final String[] period = command.substring(matcher.start(), matcher.end()).split(DATE_PERIOD_SEPARATOR);
            final LocalDateTime startDate = parseToLocalDateTime(period[START_DATE_INDEX], periodPattern);
            final LocalDateTime endDate = parseToLocalDateTime(period[END_DATE_INDEX], periodPattern);
            validatePeriod(startDate, endDate);
        }
    }

    private void validatePeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IncorrectCommandDateTimePeriodException(startDate, endDate,
                    IncorrectCommandDateTimePeriodException.IncorrectPeriodType.LESS);
        } else if (startDate.isEqual(endDate)) {
            throw new IncorrectCommandDateTimePeriodException(startDate, endDate,
                    IncorrectCommandDateTimePeriodException.IncorrectPeriodType.SAME);
        }
    }

    private LocalDateTime parseToLocalDateTime(final String date, final String periodPattern) {
        final String normalizedDate =
                periodPattern.equals(DATE_TIME_PERIOD_REGEX_PATTERN) ? date : date + DEFAULT_TIME_SUFFIX;
        return LocalDateTime.parse(normalizedDate, DATE_TIME_FORMATTER);
    }
}
