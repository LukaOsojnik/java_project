package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.exception.ReadSerializedException;
import hr.javafx.realestate.javafxmanagementsystem.model.PaidInvoice;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class ChangedInvoiceController {

    @FXML TableView<PaidInvoice> invoiceTable;
    @FXML TableColumn<PaidInvoice, String> invoiceRoleColumn;
    @FXML TableColumn<PaidInvoice, String> invoiceDateColumn;
    @FXML TableColumn<PaidInvoice, String> invoiceInvoiceColumn;
    @FXML TableColumn<PaidInvoice, String> invoicePreviousInvoiceColumn;

    public void initialize() {

        invoiceRoleColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getRole()));
        invoiceDateColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPaidDate()));
        invoiceInvoiceColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getInvoice().getLease().getTenant().getFullName()
                 + " " + celldata.getValue().getInvoice().isPaid()));
        invoicePreviousInvoiceColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getInvoice().getLease().getTenant().getFullName()
                        + " " + celldata.getValue().getChangedInvoice().isPaid()));
        deserializeAll();
    }

    public void deserializeAll() {
        try{
            List<PaidInvoice> entries = SearchInvoiceController.readAllEntries();
            ObservableList<PaidInvoice> observableList = FXCollections.observableList(entries);
            invoiceTable.setItems(observableList);
        } catch(ReadSerializedException _){
            logger.error("Failed to read serialized file.");
        }
    }


}
