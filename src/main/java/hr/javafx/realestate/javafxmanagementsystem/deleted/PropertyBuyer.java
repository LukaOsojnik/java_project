package hr.javafx.realestate.javafxmanagementsystem.deleted;

import hr.javafx.realestate.javafxmanagementsystem.model.Address;
import hr.javafx.realestate.javafxmanagementsystem.model.Person;

public class PropertyBuyer extends Person {

    private Address address;

    public PropertyBuyer(Long id, String firstName, String lastName, String contactNumber, String email, Address address){
        super(id, firstName, lastName, contactNumber, email);
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
