package hr.javafx.realestate.javafxmanagementsystem.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PaidInvoice implements Serializable {
    private static final DateTimeFormatter PAID_DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    Invoice invoice;
    Invoice changedInvoice;
    String role;
    LocalDateTime paidDate;

    public PaidInvoice(Invoice invoice, Invoice changedInvoice, String role, LocalDateTime paidDate) {
        this.invoice = invoice;
        this.changedInvoice = changedInvoice;
        this.role = role;
        this.paidDate = paidDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }
    public Invoice getChangedInvoice(){
        return changedInvoice;
    }

    public String getRole() {
        return role;
    }

    public String getPaidDate() {
        return paidDate.format(PAID_DATE_FMT);
    }
}
