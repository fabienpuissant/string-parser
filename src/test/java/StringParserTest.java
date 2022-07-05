import exceptions.EOFDigitExpectedException;
import exceptions.NegativeNumberException;
import exceptions.NullParamException;
import exceptions.TwoSeparatorInARowException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StringParserTest {

    private final StringParser stringParser = new StringParser();

    @Nested
    @DisplayName("Tests for the method parseString")
    class ParseStringTest {

        @DisplayName("it should throw an NullParamException if the param is null ")
        @Test
        void shouldThrowNullParamExceptionIfTheParamIsNull() {
            assertThrows(NullParamException.class, () -> stringParser.parse(null));
        }

        @DisplayName("it should return an array of numbers with separators , or \n")
        @Test
        void shouldReturnANumberListWhenClassicSeparatorPassed() {
            assertEquals(List.of(1, 3), stringParser.parse("1,3"));
            assertEquals(List.of(1, 3), stringParser.parse("1\n3"));
            assertEquals(List.of(1, 3, 4), stringParser.parse("1\n3,4"));
        }

        @DisplayName("it should throw a TwoSeparatorInARowException if there is two separators in a row")
        @Test
        void shouldThrowAnExceptionIfTwoSeparatorsInARow() {
            RuntimeException exception = assertThrows(TwoSeparatorInARowException.class, () -> stringParser.parse("1,\n2"));
            assertEquals("Cannot have two separators in a row", exception.getMessage());
        }

        @DisplayName("it should throw a EOFDigitExpectedException if there is two separators in a row")
        @Test
        void shouldThrowAnExceptionIfTheStringNotEndsByDigit() {
            RuntimeException exception = assertThrows(EOFDigitExpectedException.class, () -> stringParser.parse("1\n2,"));
            assertEquals("The expression must end by a number", exception.getMessage());
        }

        @DisplayName("it should not accept negative number and display them in a RuntimeException")
        @Test
        void shouldNotAcceptNegativeNumbers() {
            RuntimeException exception = assertThrows(NegativeNumberException.class, () -> stringParser.parse("1\n-2,-3"));
            assertEquals("negatives not allowed: [-2, -3]", exception.getMessage());
        }

        @DisplayName("it should not take minus separator as a negative number")
        @Test
        void shouldBeAbleToDistinguishMinusSeparatorAndNegativeNumbers() {
            assertEquals(List.of(1, 2, 3, 4), stringParser.parse("//-\n1-2-3-4"));
        }

        @DisplayName("it should convert any separator with the start //[delimiter]\\n[numbers…]")
        @Test
        void shouldReturnANumberListFromAnySeparator() {
            assertEquals(List.of(1, 2, 3), stringParser.parse("//*\n1*2*3"));
        }

        @DisplayName("it should be able to convert many character separator")
        @Test
        void shouldReturnANumberListFromAMultipleCharacterSeparator() {
            assertEquals(List.of(1, 2, 3, 4), stringParser.parse("//[&&]\n1&&2&&3&&4"));
        }

        @DisplayName("it should be able to convert multiple single character separator")
        @Test
        void shouldReturnANumberListFromASingleCharacterSeparatorAndManySeparators() {
            assertEquals(List.of(1, 2, 3, 4), stringParser.parse("//[&][*]\n1&2*3&4"));
        }

        @DisplayName("it should be able to convert many character separator and many separators")
        @Test
        void shouldReturnANumberListFromAMultipleCharacterSeparatorAndManySeparators() {
            assertEquals(List.of(1, 2, 3, 4), stringParser.parse("//[&][))]\n1&2))3&4"));
        }
    }
}
