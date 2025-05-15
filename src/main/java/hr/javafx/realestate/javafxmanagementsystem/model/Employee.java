package hr.javafx.realestate.javafxmanagementsystem.model;

import hr.javafx.realestate.javafxmanagementsystem.enum1.Role;

/**
 * Služi za kreiranje klase Employee
 */

public class Employee extends Person{

    private Role role;

    public Employee(Long id, String firstName, String lastName ,Role role, String contactNumber, String email) {
        super(id, firstName, lastName, contactNumber, email);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
