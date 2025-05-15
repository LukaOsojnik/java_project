package hr.javafx.realestate.javafxmanagementsystem.deleted;

import hr.javafx.realestate.javafxmanagementsystem.model.Person;

public class PropertyOwner extends Person {

    private String contactNumber;
    private String email;

    public PropertyOwner(Long id, String firstName, String lastName, String contactNumber, String email) {
        super(id, firstName, lastName, contactNumber, email);
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
