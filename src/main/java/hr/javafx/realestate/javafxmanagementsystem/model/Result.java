package hr.javafx.realestate.javafxmanagementsystem.model;

/**
 * Jednostavna generička klasa koja drži rezultat operacije
 * @param <T> tip rezultata
 */
public class Result<T> {
    private final T data;
    private final boolean success;
    private final String message;

    public Result(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}