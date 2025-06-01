package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.dbrepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import hr.javafx.realestate.javafxmanagementsystem.thread.DebtThread;
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
import java.sql.SQLException;
import java.util.List;

public class SearchInvoiceController {

    @FXML private TableView<Invoice> invoiceTableView;
    @FXML private TableColumn<Invoice, String> invoiceIdColumn;
    @FXML private TableColumn<Invoice, String> invoiceNameAndSurnameColumn;
    @FXML private TableColumn<Invoice, String> invoiceDueDateColumn;
    @FXML private TableColumn<Invoice, String> invoiceAmountColumn;
    @FXML private TableColumn<Invoice, String> invoiceStatusColumn;

    @FXML private Label selectedInvoiceLabel = new Label();
    @FXML private Label unPaidDebt = new Label();

    InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase<>();
    private Invoice selectedInvoice;

    private static final String SERIALIZATION_FILE = "logs/izmjene.dat";
    private static final String DESERIALIZED_LOG_FILE = "logs/izmjene.log";

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

        invoiceList.forEach(a -> ird.saveExistingInvoice(a));

        invoiceList = ird.findAll();

        ObservableList<Invoice> observableList = FXCollections.observableList(invoiceList);
        invoiceTableView.setItems(observableList);

    }
    public void currentDebt(){

        DebtThread debtThread = new DebtThread(ird, unPaidDebt);
        Thread runner = new Thread(debtThread);
        runner.start();

    }
    public void changeStatus() throws SQLException, IOException {
        if(selectedInvoice != null) {
            ird.updateStatus(selectedInvoice);
            selectedInvoice.markAsPaid();
            serialize(selectedInvoice);
        }
    }

    private void serialize(Invoice invoice) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE))) {
            oos.writeObject(invoice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZATION_FILE))) {
            Invoice invoice = (Invoice) ois.readObject();
            selectedInvoiceLabel.setText("Deserialized invoice : " + invoice.getId() + ", paid=" + invoice.isPaid());
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(DESERIALIZED_LOG_FILE, true))) {
                writer.write("Invoice ID: " + invoice.getId() + ", paid=" + invoice.isPaid());
                writer.newLine();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }






}
