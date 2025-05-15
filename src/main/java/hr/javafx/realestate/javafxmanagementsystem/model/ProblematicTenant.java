package hr.javafx.realestate.javafxmanagementsystem.model;

public non-sealed class ProblematicTenant extends Tenant implements Problematic {

    public ProblematicTenant(Long id, String firstName, String lastName, String email, String contactNumber) {
        super(id, firstName, lastName, email, contactNumber);
    }

}
