package ua.com.rafael.doit.feature.model;

import ua.com.rafael.doit.feature.exceptions.IncorrectCommandDateException;
import ua.com.rafael.doit.feature.exceptions.IncorrectCommandDateTimePeriodException;
import ua.com.rafael.doit.feature.exceptions.IncorrectCommandStructureException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ua.com.rafael.doit.feature.utils.RegexUtil.*;

public class CommonValidatorCommand extends DoitCommand {
    private static final String KEY_WORD_REGEX_COMMAND_PATTERN = "^doit [a-zA-Z]+";

    private static final String PARAMETERS_REGEX_PATTERN = "( -[a-zA-Z]+)?( --[a-zA-Z]+(-[a-zA-Z]+)?)*";

    private static final String FLAGS_ATTRIBUTES_REGEX_PATTERN =
            "( [a-zA-Z0-9]-[a-zA-Z0-9])?((?<=([a-zA-z0-9]-[a-zA-z0-9])),[a-zA-z0-9]-[a-zA-z0-9])*" +
                    "(((?<!([a-zA-z0-9]-[a-zA-z0-9])) ?[a-zA-Z]*)|((?<!([a-zA-z0-9]-[a-zA-z0-9])) \".*\"))?";

    private static final String DATE_REGEX_PATTERN = "(2[0-9]{3}[.](0[1-9]|1[0-2])[.](0[1-9]|1[0-9]|2[1-9]|3[0-1]))";
    private static final String TIME_REGEX_PATTERN = "(0[0-9]|1[0-2]):[0-5][0-9]:[0-5][0-9](am|pm)";

    private static final String DATE_TIME_SEPARATOR_REGEX = "-";
    private static final String DATE_TIME_REGEX_PATTERN = DATE_REGEX_PATTERN + DATE_TIME_SEPARATOR_REGEX + TIME_REGEX_PATTERN;

    private static final String PERIOD_SEPARATOR_REGEX = "--";
    private static final String PERIOD_DATE_REGEXP_PATTERN = DATE_REGEX_PATTERN + PERIOD_SEPARATOR_REGEX + DATE_REGEX_PATTERN;
    private static final String PERIOD_DATE_TIME_REGEX_PATTERN = DATE_TIME_REGEX_PATTERN + PERIOD_SEPARATOR_REGEX + DATE_TIME_REGEX_PATTERN;

    private static final String CORRECT_COMMAND_PATTERN_ =
            KEY_WORD_REGEX_COMMAND_PATTERN +
                    PARAMETERS_REGEX_PATTERN +
                    FLAGS_ATTRIBUTES_REGEX_PATTERN +
                    groupOf(
                            groupOf(WHITE_SPACE + DATE_REGEX_PATTERN + groupOf(DATE_TIME_SEPARATOR_REGEX + TIME_REGEX_PATTERN) + AT_MOST_ONCE) +
                                    OR +
                                    groupOf(WHITE_SPACE + PERIOD_DATE_REGEXP_PATTERN) +
                                    OR +
                                    groupOf(WHITE_SPACE + PERIOD_DATE_TIME_REGEX_PATTERN)
                    ) + AT_MOST_ONCE;

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH:mm:ss a");
    private final static String AM_SUFFIX = " AM";
    private final static String PM_SUFFIX = " PM";
    private final static String DEFAULT_TIME_SUFFIX = "-00:00:00" + AM_SUFFIX;
    private final static String AM = "am";
    private final static String PM = "pm";
    private final static String AM_REGEX_PATTERN = NOTHING_OR_ANY_AMOUNT_OF_SYMBOLS + AM + LINE_END;

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
        if (!command.matches(CORRECT_COMMAND_PATTERN_)) {
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
        if (command.matches(NOTHING_OR_ANY_AMOUNT_OF_SYMBOLS + PERIOD_DATE_REGEXP_PATTERN)) {
            validatePeriod(command, PERIOD_DATE_REGEXP_PATTERN);
        } else {
            validatePeriod(command, PERIOD_DATE_TIME_REGEX_PATTERN);
        }
    }

    private void validatePeriod(final String command,
                                final String periodPattern) {
        final Pattern pattern = Pattern.compile(periodPattern);
        final Matcher matcher = pattern.matcher(command);

        if (matcher.find()) {
            final String[] period = command.substring(matcher.start(), matcher.end()).split(PERIOD_SEPARATOR_REGEX);
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
        final String normalizedDate = normalizeDate(date, periodPattern);
        return LocalDateTime.parse(normalizedDate, DATE_TIME_FORMATTER);
    }

    private String normalizeDate(final String date, final String periodPattern) {
        if (periodPattern.equals(PERIOD_DATE_TIME_REGEX_PATTERN)) {
            return date.matches(AM_REGEX_PATTERN) ? date.replace(AM, AM_SUFFIX) : date.replace(PM, PM_SUFFIX);
        }
        return date + DEFAULT_TIME_SUFFIX;
    }
}
