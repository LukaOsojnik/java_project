package hr.javafx.realestate.javafxmanagementsystem.model;

/**
 * Jednostavna generička klasa koja drži par vrijednosti
 * @param <T> tip prve vrijednosti
 * @param <U> tip druge vrijednosti
 */
public class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }
}