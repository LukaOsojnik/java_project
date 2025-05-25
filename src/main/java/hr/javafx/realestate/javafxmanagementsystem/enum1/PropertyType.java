package hr.javafx.realestate.javafxmanagementsystem.enum1;

public enum PropertyType {

    APARTMENT("Stan"),
    HOUSE("Kuća"),
    LAND("Zemljište");

    private final String value;

    PropertyType(String value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return this.value;
    }

}
