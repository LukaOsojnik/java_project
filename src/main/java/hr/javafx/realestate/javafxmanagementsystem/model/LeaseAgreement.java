package hr.javafx.realestate.javafxmanagementsystem.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Služi za kreiranje odnosa između klase Tenant, Property-a i Invoice-a
 */

public class LeaseAgreement extends Entity{

    private Tenant tenant;
    private Property property;
    private BigDecimal rentPrice;
    private LocalDate signingDate;

    public LeaseAgreement(Long id, Tenant tenant, Property property, BigDecimal rentPrice, LocalDate signingDate) {
        super(id);
        this.tenant = tenant;
        this.property = property;
        this.rentPrice = rentPrice;
        this.signingDate = signingDate;
    }
    public LeaseAgreement(Tenant tenant, Property property, BigDecimal rentPrice, LocalDate signingDate) {
        this.tenant = tenant;
        this.property = property;
        this.rentPrice = rentPrice;
        this.signingDate = signingDate;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }
    public LocalDate getSigningDate() {
        return signingDate;
    }

    /**
     * Poziva se nakon kreiranja klase LeaseAgreement te služi za dodavanja računa u klasu Tenant
     */

    public void generateAndAddInvoice(){
        LocalDate paymentDueDate = signingDate.plusMonths(tenant.getInvoices().size());
        Invoice invoice = new Invoice(this, paymentDueDate, rentPrice);

        this.tenant.addInvoice(invoice);
    }

}
