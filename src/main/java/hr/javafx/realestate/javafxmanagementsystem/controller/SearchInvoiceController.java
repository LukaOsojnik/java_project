package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.DbRepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SearchInvoiceController {

    @FXML private TableView<Invoice> invoiceTableView;
    @FXML private TableColumn<Invoice, String> invoiceIdColumn;
    @FXML private TableColumn<Invoice, String> invoiceNameAndSurnameColumn;
    @FXML private TableColumn<Invoice, String> invoiceDueDateColumn;
    @FXML private TableColumn<Invoice, String> invoiceAmountColumn;
    @FXML private TableColumn<Invoice, String> invoiceStatusColumn;

    public void initialize() {
        invoiceIdColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getId().toString()));
        invoiceNameAndSurnameColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getLease().getTenant().getFullName()));
        invoiceDueDateColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getDueDate().toString()));
        invoiceAmountColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getLease().getRentPrice().toString()));
        invoiceStatusColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().isPaid().toString()));
    }

    public void checkInvoice() throws SQLException, IOException {
        InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase();

        List<Invoice> invoiceList = ird.nextMonthInvoice();

        for(Invoice i : invoiceList) {
            ird.saveExistingInvoice(i);
        }

        invoiceList = ird.findAll();

        ObservableList<Invoice> observableList = FXCollections.observableList(invoiceList);
        invoiceTableView.setItems(observableList);

    }

}
