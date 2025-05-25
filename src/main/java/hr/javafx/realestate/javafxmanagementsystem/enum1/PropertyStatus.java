package hr.javafx.realestate.javafxmanagementsystem.enum1;

public enum PropertyStatus {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not available");

    private final String value;

    PropertyStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
