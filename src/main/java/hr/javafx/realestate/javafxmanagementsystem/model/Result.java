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

    public static <T> Result<T> success(T data) {
        return new Result<>(data, true, null);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(null, false, message);
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