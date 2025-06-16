package hr.javafx.realestate.javafxmanagementsystem.exception;

public class ReadSerializedException extends Exception {

    public ReadSerializedException() {
        super();
    }
    public ReadSerializedException(String message) {
        super(message);
    }


    public ReadSerializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadSerializedException(Throwable cause) {
        super(cause);
    }

    protected ReadSerializedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
