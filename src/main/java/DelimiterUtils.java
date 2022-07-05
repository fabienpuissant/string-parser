import enums.DelimiterType;
import exceptions.NullParamException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static enums.DelimiterType.*;

public class DelimiterUtils {

    public static final List<String> BASIC_SEPARATORS = List.of(",", "\n");
    public static final String REGEX_SEPARATOR_DELIMITER = "//|\n";
    public static final String COMPLEX_PATTERN_ENCAPSULATION = "\\[(.*?)]";
    public static final String DELIMITER_PATTERN_ENCAPSULATION = "//(.*?)\n";
    private static final String regexSeparatorDelimiter = "//|\n";


    /**
     * Find the delimiters in the input String
     *
     * @param numbers The string input
     * @return List<String>
     */
    public static List<String> getDelimiters(String numbers) {
        if (numbers == null) throw new NullParamException("numbers");
        DelimiterType delimiterType = findDelimiterType(numbers);
        switch (delimiterType) {
            case SIMPLE_SEPARATOR -> {
                var separatorsByteArray = numbers.split(REGEX_SEPARATOR_DELIMITER);
                var separatorsArray = separatorsByteArray[1];
                return List.of(separatorsArray);
            }
            case COMPLEX_SEPARATOR -> {
                List<String> separatorList = new ArrayList<>();
                Matcher m = Pattern.compile(COMPLEX_PATTERN_ENCAPSULATION).matcher(numbers);
                while (m.find()) {
                    separatorList.add(m.group(1));
                }
                return separatorList;
            }
            default -> {
                return DelimiterUtils.BASIC_SEPARATORS;
            }
        }
    }

    /**
     * Return the delimiter type of the input
     *
     * @return DelimiterType
     */
    private static DelimiterType findDelimiterType(String inputString) {
        if (Character.isDigit(inputString.charAt(0))) {
            return NATIVE_SEPARATOR;
        } else {
            var separatorsByteArray = inputString.split(regexSeparatorDelimiter);
            String separators = Arrays.stream(separatorsByteArray).toList().get(1);
            var isBasicSeparatorWithOneCharacter = separators.length() == 1;
            if (isBasicSeparatorWithOneCharacter) {
                return SIMPLE_SEPARATOR;
            } else {
                return COMPLEX_SEPARATOR;
            }
        }
    }

    /**
     * Convert a list of separator into a regex expression
     *
     * @param input initial input string
     * @return String
     */
    public static String getDelimiterRegex(String input) {
        var delimiters = getDelimiters(input);
        if (delimiters == null) throw new NullParamException("delimiters");
        boolean isOnlyOneSeparator = delimiters.size() == 1 && delimiters.get(0).length() != 1;
        if (isOnlyOneSeparator) {
            return delimiters.get(0);
        }

        String regex = delimiters.stream()
                .reduce("(", (subtotal, element) -> {
                    var chars = element.toCharArray();
                    StringBuilder formattedElement = new StringBuilder();
                    // Escape special characters
                    for (char ch : chars) {
                        formattedElement.append("\\").append(ch);
                    }
                    return subtotal + formattedElement + "|";

                });
        regex = regex.substring(0, regex.length() - 1);
        regex += ")";
        return regex;
    }

    /**
     * Remove the delimiter definition from the input
     *
     * @return String
     */
    public static String stripDelimiters(String numbers) {
        if (numbers == null) throw new NullParamException("numbers");
        return numbers.replaceAll(DelimiterUtils.DELIMITER_PATTERN_ENCAPSULATION, "");
    }
}
