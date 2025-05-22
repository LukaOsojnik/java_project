package hr.javafx.realestate.javafxmanagementsystem.exception;

public class FailedToAuthenticateException extends RuntimeException {
    public FailedToAuthenticateException() {
    }

    public FailedToAuthenticateException(String message) {
        super(message);
    }

    public FailedToAuthenticateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToAuthenticateException(Throwable cause) {
        super(cause);
    }

    public FailedToAuthenticateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
