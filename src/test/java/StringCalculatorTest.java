import exceptions.NullParamException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {

    private final StringCalculator stringCalculator = new StringCalculator();

    @Nested
    @DisplayName("Tests for the method Add")
    class AddTest {
        @DisplayName("it should return 0 for an empty string")
        @Test
        void shouldReturnZeroForEmptyString() {
            assertEquals(0, stringCalculator.add(""));
            assertEquals(0, stringCalculator.add("   "));
        }

        @DisplayName("it should throw an NullParamException if we give a null string")
        @Test
        void shouldThrowAnNullParamExceptionIfInputIsNull() {
            assertThrows(NullParamException.class, () -> stringCalculator.add(null));
        }

        @DisplayName("it should return the sum of two input separated by a comma")
        @Test
        void shouldReturnSumOfInputSeparatedByComma() {
            assertEquals(3, stringCalculator.add("1,2"));
        }

        @DisplayName("it should return the sum of any number of inputs separated by a comma")
        @Test
        void shouldReturnSumManyInputSeparatedByComma() {
            assertEquals(10, stringCalculator.add("1,2,3,4"));
        }


        @DisplayName("it should accept comma and \n separator character")
        @Test
        void shouldAcceptCommaAndNewLineAsSeparator() {
            assertEquals(10, stringCalculator.add("1\n2,3\n4"));
        }

        @DisplayName("it should accept any separator with the start //[delimiter]\\n[numbers…]")
        @Test
        void shouldAcceptAnySeparator() {
            assertEquals(10, stringCalculator.add("//*\n1*2*3*4"));
        }


        @DisplayName("it should ignore number more than 1000")
        @Test
        void shouldIgnoreNumberMoreThanThousand() {
            assertEquals(1002, stringCalculator.add("1000,2"));
            assertEquals(2, stringCalculator.add("1001,2"));
        }

        @DisplayName("it should be able to detect many character separator")
        @Test
        void shouldBeAbleToTakeAMultipleCharacterSeparator() {
            assertEquals(10, stringCalculator.add("//[&&]\n1&&2&&3&&4"));
        }

        @DisplayName("it should be able to detect multiple single character separator")
        @Test
        void shouldBeAbleToTakeASingleCharacterSeparatorAndManySeparators() {
            assertEquals(10, stringCalculator.add("//[&][*]\n1&2*3&4"));
        }

        @DisplayName("it should be able to detect many character separator and many separators")
        @Test
        void shouldBeAbleToTakeAMultipleCharacterSeparatorAndManySeparators() {
            assertEquals(10, stringCalculator.add("//[&][))]\n1&2))3&4"));
        }
    }
}
