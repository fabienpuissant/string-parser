package exceptions;

public class TwoSeparatorInARowException extends IllegalArgumentException {

    public TwoSeparatorInARowException() {
        super("Cannot have two separators in a row");
    }

}
