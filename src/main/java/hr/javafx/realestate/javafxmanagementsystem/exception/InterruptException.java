package hr.javafx.realestate.javafxmanagementsystem.exception;

public class InterruptException extends RuntimeException {
    public InterruptException(String message) {
        super(message);
    }

    public InterruptException() {
        super();
    }

    public InterruptException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterruptException(Throwable cause) {
        super(cause);
    }

    protected InterruptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
