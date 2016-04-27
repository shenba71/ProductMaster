package com.schawk.productmaster.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility class for String Operations
 * @author shenbagaganesh.param
 *
 */
public class StringUtil {

    private static final String DELIMITER_UNDERSCORE = "_";
    private static final String DELIMITER_HYPEN = "-";
    private static final String DELIMITER_WHITESPACE = " ";

    /**
     * converts the input string into camelCase format
     * @param value
     * @return the input string in camelCase format
     */
    public static String getCamelCase(String value) {
        String result = value;
        String[] parts = {};
        String delimiter = getDelimiter(value);

        if (delimiter != null) {
            parts = value.split(delimiter);
            result = parts[0].toLowerCase()
                    + Arrays.stream(parts)
                            .skip(1)
                            .map(part -> part.substring(0, 1).toUpperCase()
                                    + part.substring(1).toLowerCase())
                            .collect(Collectors.joining());
        }

        return result;
    }

    /**
     * returns the delimiter present in the given string
     * @param value
     * @return the delimiter in the input string
     */
    public static String getDelimiter(String value) {
        String delimiter = null;

        if (value != null) {
            if (value.contains(DELIMITER_UNDERSCORE)) {
                delimiter = DELIMITER_UNDERSCORE;
            } else if (value.contains(DELIMITER_HYPEN)) {
                delimiter = DELIMITER_HYPEN;
            } else if (value.contains(DELIMITER_WHITESPACE)) {
                delimiter = DELIMITER_WHITESPACE;
            }
        }

        return delimiter;
    }

}
