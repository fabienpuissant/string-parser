package exceptions;

public class EOFDigitExpectedException extends IllegalArgumentException{

    public EOFDigitExpectedException() {
        super("The expression must end by a number");
    }

}
