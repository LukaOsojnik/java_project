package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.dbrepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class SearchInvoiceController {

    @FXML private TableView<Invoice> invoiceTableView;
    @FXML private TableColumn<Invoice, String> invoiceIdColumn;
    @FXML private TableColumn<Invoice, String> invoiceNameAndSurnameColumn;
    @FXML private TableColumn<Invoice, String> invoiceDueDateColumn;
    @FXML private TableColumn<Invoice, String> invoiceAmountColumn;
    @FXML private TableColumn<Invoice, String> invoiceStatusColumn;

    @FXML private Label selectedInvoiceLabel = new Label();
    InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase<>();
    private Invoice selectedInvoice;

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

        invoiceTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Invoice selected = invoiceTableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selectedInvoice = selected;
                    selectedInvoiceLabel.setText("Selected invoice : " + selectedInvoice.getId());
                }
            }
        });

        Timeline refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    try {
                        checkInvoice();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                })
        );

        refreshTimeline.setCycleCount(Animation.INDEFINITE);

        refreshTimeline.play();



    }

    public void checkInvoice() throws SQLException, IOException {

        List<Invoice> invoiceList = ird.nextMonthInvoice();

        for(Invoice i : invoiceList) {
            ird.saveExistingInvoice(i);
        }

        invoiceList = ird.findAll();

        ObservableList<Invoice> observableList = FXCollections.observableList(invoiceList);
        invoiceTableView.setItems(observableList);

    }

    public void changeStatus() throws SQLException, IOException {

        ird.updateStatus(selectedInvoice);

    }






}
