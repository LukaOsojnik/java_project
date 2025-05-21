package hr.javafx.realestate.javafxmanagementsystem.model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Služi za kreiranje računa koji se sprema u klasi Tenant
 */

public class Invoice extends Entity {

    private LeaseAgreement lease;
    private LocalDate dueDate;
    private Boolean isPaid;
    private BigDecimal rentPrice;

    public Invoice(Long id, LeaseAgreement lease,  LocalDate dueDate) {
        super(id);
        this.lease = lease;
        this.dueDate = dueDate;
        this.isPaid = false;
    }
    public Invoice(LeaseAgreement lease, LocalDate dueDate) {
        this.lease = lease;
        this.dueDate = dueDate;
        this.isPaid = false;
    }

    public Invoice(Long id, LeaseAgreement lease, LocalDate dueDate, Boolean isPaid, BigDecimal rentPrice) {
        super(id);
        this.lease = lease;
        this.dueDate = dueDate;
        this.rentPrice = rentPrice;
        this.isPaid = isPaid;
    }

    public LeaseAgreement getLease() {
        return lease;
    }

    public void setLease(LeaseAgreement lease) {
        this.lease = lease;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public Boolean isPaid() {
        return isPaid;

    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }
}
