package ua.com.rafael.doit.feature.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RegexUtil {

    public final String OR = "|";
    public final String WHITE_SPACE = " ";
    public final String AT_MOST_ONCE = "?";
    public final String NOTHING_OR_ANY_AMOUNT_OF_SYMBOLS = ".*";
    public final String LINE_END = "$";

    public String groupOf(final String regex) {
        return String.format("(%s)", regex);
    }
}
