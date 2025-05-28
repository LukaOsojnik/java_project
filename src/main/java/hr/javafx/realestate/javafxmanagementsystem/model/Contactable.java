package hr.javafx.realestate.javafxmanagementsystem.model;

public interface Contactable {
    String getContactNumber();
    String getEmail();

    default String getContactInfo() {
        return "Phone: " + getContactNumber() + ", Email: " + getEmail();
    }

}
