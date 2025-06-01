package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.dbrepository.InboxRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.dbrepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SearchInboxController {

    @FXML private TableView<InboxMessage> inboxTableView;
    @FXML private TableColumn<InboxMessage, String> invoiceIdColumn;
    @FXML private TableColumn<InboxMessage, String> creationDateColumn;
    @FXML private TableColumn<InboxMessage, String> messageColumn;

    @FXML private Label selectedInboxLabel = new Label();

    private Long selectedInvoiceId;


    private static final String INBOX_MESSAGE = "Imate nedospjelu ratu. Molim Vas podmirite račun.";
    InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase<>();
    InboxRepositoryDatabase<InboxMessage> inboxRDB = new InboxRepositoryDatabase<>();

    public void initialize() {

        invoiceIdColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getInvoiceId().toString()));
        messageColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMessage()));
        creationDateColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getReminderDate().toString()));

        inboxTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                InboxMessage selected = inboxTableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selectedInvoiceId = selected.getInvoiceId();
                    selectedInboxLabel.setText(ird.findById(selectedInvoiceId).getLease().getTenant().getFullName());
                }
            }
        });

        Timeline refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    try {
                        refreshInbox();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                })
        );

        refreshTimeline.setCycleCount(Animation.INDEFINITE);

        refreshTimeline.play();
    }

    public void refreshInbox() throws SQLException, IOException {

        List<InboxMessage> inboxList;

        inboxList = inboxRDB.findAll();
        List<Long> inboxInvoiceId = inboxList.stream()
                .map(InboxMessage::getInvoiceId)
                .toList();

        List<Invoice> invoiceList = ird.findAll();

        for(Invoice i : invoiceList) {
            if(!inboxInvoiceId.contains(i.getId()) && !i.isPaid().booleanValue()) {
                InboxMessage inboxMsg = new InboxMessage(i.getId(), INBOX_MESSAGE, LocalDate.now());
                inboxList.add(inboxMsg);
                inboxRDB.save(inboxMsg);
            }
        }


        List<InboxMessage> listOfUnpaid = inboxRDB.checkForUnpaid();


        listOfUnpaid.stream()
                .filter(i -> !ird.findById(i.getInvoiceId()).isPaid().booleanValue())
                .forEach(i ->inboxRDB.save(i));


        ObservableList<InboxMessage> observableList = FXCollections.observableList(inboxList);
        inboxTableView.setItems(observableList);

    }









}