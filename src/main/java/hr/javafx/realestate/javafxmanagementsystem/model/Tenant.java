package hr.javafx.realestate.javafxmanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Služi za kreiranje klase Tenant
 */

public sealed class Tenant extends Person permits ProblematicTenant {

    private List<Invoice> invoices = new ArrayList<>();

    public Tenant(Long id, String firstName, String lastName, String contactNumber, String email) {
        super(id, firstName, lastName, contactNumber, email);
    }
    public Tenant(String firstName, String lastName, String contactNumber, String email) {
        super(firstName, lastName, contactNumber, email);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }
}
