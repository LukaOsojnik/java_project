package hr.javafx.realestate.javafxmanagementsystem.thread;

import hr.javafx.realestate.javafxmanagementsystem.dbrepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import javafx.application.Platform;
import javafx.scene.control.Label;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;


public class DebtThread implements Runnable {

    private InvoiceRepositoryDatabase<Invoice> ird;
    private Label debt;

    public DebtThread(InvoiceRepositoryDatabase<Invoice> ird, Label debt) {
        this.ird = ird;
        this.debt = debt;
    }

    @Override
    public void run() {
        Optional<BigDecimal> totalDebt;
        totalDebt = ird.findAll().stream()
                .filter(i -> !i.isPaid().booleanValue())
                .map(Invoice::getRentPrice)
                .reduce(BigDecimal::add);

        Platform.runLater(() -> debt.setText(totalDebt.get().toString()));
    }
}
