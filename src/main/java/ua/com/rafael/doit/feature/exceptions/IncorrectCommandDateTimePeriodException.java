package ua.com.rafael.doit.feature.exceptions;

import java.time.LocalDateTime;

public class IncorrectCommandDateTimePeriodException extends RuntimeException {

    public IncorrectCommandDateTimePeriodException(final LocalDateTime startDate, final LocalDateTime endDate,
                                                   IncorrectPeriodType incorrectPeriodType) {
        super(String.format(incorrectPeriodType.exceptionMessageTemplate, startDate, endDate));
    }

    public enum IncorrectPeriodType {
        LESS("End date %s is less than start date %s."),
        SAME("Start date %s is the same as end date %s.");

        private final String exceptionMessageTemplate;

        IncorrectPeriodType(String exceptionMessageTemplate) {
            this.exceptionMessageTemplate = exceptionMessageTemplate;
        }
    }
}
