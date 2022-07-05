import exceptions.NullParamException;

public class StringCalculator {

    private final StringParser stringParser = new StringParser();

    /**
     * Function that will sum the positive numbers found in the string depending on the separators provided
     * at the beginning of it
     *
     * @return int The sum, can throw RunTimeException on error
     */
    int add(String numbers) {
        if (numbers == null) throw new NullParamException("numbers");
        // If we have a bank string we return 0
        boolean isInputBlank = numbers.trim().equals("");
        if (isInputBlank) {
            return 0;
        }
        var intArray = stringParser.parse(numbers);
        return intArray.stream().reduce(0, Integer::sum);
    }
}
