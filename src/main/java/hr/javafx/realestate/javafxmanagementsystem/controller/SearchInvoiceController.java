package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.dbrepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.exception.ReadSerializedException;
import hr.javafx.realestate.javafxmanagementsystem.filerepository.LoginRepository;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import hr.javafx.realestate.javafxmanagementsystem.model.LogIn;
import hr.javafx.realestate.javafxmanagementsystem.model.PaidInvoice;
import hr.javafx.realestate.javafxmanagementsystem.thread.DebtThread;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @FXML private Label unPaidDebt = new Label();

    InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase<>();
    private Invoice selectedInvoice;

    private static final String SERIALIZATION_FILE = "logs/izmjene.dat";

    private LogIn user = LoginRepository.getCurrentUser();
    private String role = user.getRole();

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
                new KeyFrame(Duration.seconds(1), event ->
                    checkInvoice()
                )
        );

        refreshTimeline.setCycleCount(Animation.INDEFINITE);

        refreshTimeline.play();
    }

    public void checkInvoice() {

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
    public void changeStatus() {
        if(selectedInvoice != null && !selectedInvoice.isPaid()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("POTVRDA");
            alert.setHeaderText("Plaćen račun");
            alert.setContentText("Kliknite ako ste sigurni da je račun plaćen.");
            alert.showAndWait();
            Invoice changedInvoice = new Invoice(selectedInvoice.getId(), selectedInvoice.getLease(),
                    selectedInvoice.getDueDate(), selectedInvoice.isPaid(), selectedInvoice.getRentPrice());
            ird.updateStatus(selectedInvoice);
            selectedInvoice.markAsPaid();
            serialize(selectedInvoice, changedInvoice);
        }
    }

    private void serialize(Invoice invoice, Invoice changedInvoice) {
        List<PaidInvoice> entries = new ArrayList<>();
        try{
            entries = readAllEntries();

        } catch(ReadSerializedException e){
            logger.error("Failed to read serialized file.");
        }
        entries.add(new PaidInvoice(invoice, changedInvoice, role, LocalDateTime.now()));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE))) {
            oos.writeObject(entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<PaidInvoice> readAllEntries() throws ReadSerializedException {
        File file = new File(SERIALIZATION_FILE);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            return (List<PaidInvoice>) obj;

        }  catch (IOException | ClassNotFoundException e) {
            throw new ReadSerializedException(e);
        }
    }









}
