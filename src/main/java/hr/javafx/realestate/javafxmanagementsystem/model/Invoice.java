package hr.javafx.realestate.javafxmanagementsystem.model;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Služi za kreiranje računa koji se sprema u klasi Tenant
 */

public class Invoice extends Entity implements Serializable {

    /** Ugovor na koji se račun odnosi. */
    private LeaseAgreement lease;

    /** Datum dospijeća računa. */
    private LocalDate dueDate;

    /** Zastavica označava je li račun plaćen. */
    private Boolean isPaid;

    /** Iznos najamnine za obračunsko razdoblje. */
    private BigDecimal rentPrice;

    // ------------------------------------------------------------------
    // KONSTRUKTORI
    // ------------------------------------------------------------------

    /**
     * Kreira novi račun i odmah ga veže uz postojeći ID entiteta.
     *
     * @param id        primarni ključ računa
     * @param lease     ugovor o najmu
     * @param dueDate   datum dospijeća računa
     */
    public Invoice(Long id, LeaseAgreement lease, LocalDate dueDate) {
        super(id);
        this.lease = lease;
        this.dueDate = dueDate;
        this.isPaid = false;
    }

    /**
     * Kreira novi račun bez ručnog zadavanja ID-a
     * (ID će biti dodijeljen naknadno u repozitoriju/bazi).
     *
     * @param lease     ugovor o najmu
     * @param dueDate   datum dospijeća računa
     */
    public Invoice(LeaseAgreement lease, LocalDate dueDate) {
        this( null, lease, dueDate );
    }

    /**
     * Kreira račun s kompletnim setom podataka.
     *
     * @param id         primarni ključ
     * @param lease      ugovor o najmu
     * @param dueDate    datum dospijeća
     * @param isPaid     status plaćanja
     * @param rentPrice  iznos najamnine
     */
    public Invoice(
            Long id,
            LeaseAgreement lease,
            LocalDate dueDate,
            Boolean isPaid,
            BigDecimal rentPrice
    ) {
        super(id);
        this.lease      = lease;
        this.dueDate    = dueDate;
        this.isPaid     = isPaid;
        this.rentPrice  = rentPrice;
    }

    /**
     * Vraća ugovor o najmu.
     */
    public LeaseAgreement getLease() {
        return lease;
    }

    /**
     * Postavlja ugovor o najmu.
     *
     * @param lease ugovor o najmu
     */
    public void setLease(LeaseAgreement lease) {
        this.lease = lease;
    }

    /**
     * Datum dospijeća računa.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Postavlja datum dospijeća.
     *
     * @param dueDate novi datum dospijeća
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return {@code true} ako je račun plaćen, inače {@code false}
     */
    public Boolean isPaid() {
        return isPaid;
    }

    /**
     * Označava račun kao plaćen.
     */
    public void markAsPaid() {
        this.isPaid = true;
    }

    /**
     * Iznos najamnine.
     */
    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    /**
     * Postavlja iznos najamnine.
     *
     * @param rentPrice novi iznos
     */
    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }
}

