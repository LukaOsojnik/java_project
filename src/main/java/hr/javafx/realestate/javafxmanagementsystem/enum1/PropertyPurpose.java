package hr.javafx.realestate.javafxmanagementsystem.enum1;

public enum PropertyPurpose {
    FOR_RENT("Za rentu"),
    FOR_SALE("Za prodaju");

    private final String value;
    PropertyPurpose(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
