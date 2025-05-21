package hr.javafx.realestate.javafxmanagementsystem.model;

import java.time.LocalDate;

public class InboxMessage extends Entity {

    private Long invoiceId;
    private String message;
    private LocalDate reminderDate;

    public InboxMessage(Long id, Long invoiceId, String message, LocalDate reminderDate) {
        super(id);
        this.invoiceId = invoiceId;
        this.message = message;
        this.reminderDate = reminderDate;
    }

    public InboxMessage(Long invoiceId, String message, LocalDate reminderDate) {
        this.invoiceId = invoiceId;
        this.message = message;
        this.reminderDate = reminderDate;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

}