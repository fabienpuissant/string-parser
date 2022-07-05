import exceptions.EOFDigitExpectedException;
import exceptions.NegativeNumberException;
import exceptions.NullParamException;
import exceptions.TwoSeparatorInARowException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringParser {

    /**
     * Return an ArrayList of numbers
     *
     * @param input The input string with potentially initial separators
     * @return List<Integer>
     */
    public List<Integer> parse(String input) {
        if (input == null) throw new NullParamException("numbers");
        boolean isLastCharacterNotDigit = !Character.isDigit(input.charAt(input.length() - 1));
        if (isLastCharacterNotDigit) {
            throw new EOFDigitExpectedException();
        }
        String regex = DelimiterUtils.getDelimiterRegex(input);
        String numbersStr = DelimiterUtils.stripDelimiters(input);
        var items = numbersStr.split(regex);
        var numbers = Arrays.stream(items).map(stringNumber -> {
            // If we have a blank, it means that there were two separators in a row
            if (stringNumber.equals("")) {
                throw new TwoSeparatorInARowException();
            }
            try {
                return Integer.parseInt(stringNumber);
            } catch (NumberFormatException ex) {
                return 0;
            }
        }).toList();
        // If negative detected, throw negative number exception
        assertOnlyPositiveNumbers(numbers);
        return numbers.stream().filter(i -> i < 1001).toList();
    }

    /**
     * Throw NegativeNumberException if negative number detected
     */
    private void assertOnlyPositiveNumbers(List<Integer> numbers) {
        var negativeItems = numbers.stream().filter(i -> i < 0).toList();
        if (negativeItems.size() > 0) {
            throw new NegativeNumberException(negativeItems);
        }
    }

}
