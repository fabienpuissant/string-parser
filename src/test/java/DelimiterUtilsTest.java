import exceptions.NullParamException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DelimiterUtilsTest {

    @Nested
    @DisplayName("Test for the method getDelimiters")
    class GetDelimitersTest {

        @DisplayName("it should throw an NullParamException if the param is null ")
        @Test
        void shouldThrowNullParamExceptionIfTheParamIsNull() {
            assertThrows(NullParamException.class, () -> DelimiterUtils.getDelimiters(null));
        }

        @DisplayName("it should accept any separator with the start //[delimiter]\\n[numbers…]")
        @Test
        void shouldDetectAnySimpleSeparator() {
            assertEquals(List.of("*"), DelimiterUtils.getDelimiters("//*\n1*2*3*4"));
        }


        @DisplayName("it should be able to detect many character separator")
        @Test
        void shouldBeAbleToDetectAMultipleCharacterSeparator() {
            assertEquals(List.of("****"), DelimiterUtils.getDelimiters("//[****]\n1****2"));
        }

        @DisplayName("it should be able to detect multiple single character separator")
        @Test
        void shouldBeAbleToDetectASingleCharacterSeparatorAndManySeparators() {
            assertEquals(List.of("&", "+"), DelimiterUtils.getDelimiters("//[&][+]\n1&2*3&4"));
        }

        @DisplayName("it should be able to detect many character separator and many separators")
        @Test
        void shouldBeAbleToDetectAMultipleCharacterSeparatorAndManySeparators() {
            assertEquals(List.of("&", "**"), DelimiterUtils.getDelimiters("//[&][**]\n1&2))3&4"));
        }
    }

    @Nested
    @DisplayName("Test for the method getDelimiterRegex")
    class GetDelimiterRegexTest {
        @DisplayName("it should be throw NullParamException when null passed")
        @Test
        void shouldThrowNullParamExceptionWhenNullPassed() {
            assertThrows(NullParamException.class, () -> DelimiterUtils.getDelimiterRegex(null));
        }

        @DisplayName("it should be return the regex match to split the input")
        @Test
        void shouldBeAbleToConvertToRegexAnySeparatorType() {
            assertEquals("(\\*)", DelimiterUtils.getDelimiterRegex("//*\n1*2*3*4"));
            assertEquals("****", DelimiterUtils.getDelimiterRegex("//[****]\n1****2"));
            assertEquals("(\\&|\\*\\*)", DelimiterUtils.getDelimiterRegex("//[&][**]\n1&2))3&4"));
            assertEquals("(\\&|\\+)", DelimiterUtils.getDelimiterRegex("//[&][+]\n1&2*3&4"));
        }
    }

    @Nested
    @DisplayName("Tests for the method stripDelimiters")
    class StripDelimiterTest {
        @DisplayName("it should be throw NullParamException when null passed")
        @Test
        void shouldThrowNullParamExceptionWhenNullPassed() {
            assertThrows(NullParamException.class, () -> DelimiterUtils.stripDelimiters(null));
        }

        @DisplayName("it should delete the separator definition")
        @Test
        void shouldRemoveDelimiterDefinition() {
            assertEquals("1*2*3*4", DelimiterUtils.stripDelimiters("//*\n1*2*3*4"));
            assertEquals("1****2", DelimiterUtils.stripDelimiters("//[****]\n1****2"));
            assertEquals("1&2))3&4", DelimiterUtils.stripDelimiters("//[&][**]\n1&2))3&4"));
            assertEquals("1&2*3&4", DelimiterUtils.stripDelimiters("//[&][+]\n1&2*3&4"));
        }
    }
}
