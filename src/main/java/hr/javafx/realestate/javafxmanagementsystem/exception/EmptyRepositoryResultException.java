package hr.javafx.realestate.javafxmanagementsystem.exception;

public class EmptyRepositoryResultException extends RuntimeException {
  public EmptyRepositoryResultException() {
  }

  public EmptyRepositoryResultException(String message) {
    super(message);
  }

  public EmptyRepositoryResultException(String message, Throwable cause) {
    super(message, cause);
  }

  public EmptyRepositoryResultException(Throwable cause) {
    super(cause);
  }

  public EmptyRepositoryResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
