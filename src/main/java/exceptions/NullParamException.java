package exceptions;

public class NullParamException extends NullPointerException {

    public NullParamException(String paramName) {
        super(paramName + " must not be null");
    }

}
