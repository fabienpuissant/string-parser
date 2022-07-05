package exceptions;

import java.util.List;

public class NegativeNumberException extends IllegalArgumentException {

    public NegativeNumberException(List<Integer> items) {
        super("negatives not allowed: " + items.toString());
    }

}
