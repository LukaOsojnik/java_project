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

import java.time.LocalDate;
import java.util.List;

public class SearchInboxController {

    @FXML private TableView<InboxMessage> inboxTableView;
    @FXML private TableColumn<InboxMessage, String> invoiceIdColumn;
    @FXML private TableColumn<InboxMessage, String> creationDateColumn;
    @FXML private TableColumn<InboxMessage, String> messageColumn;

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

        Timeline refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event ->
                    refreshInbox())
        );

        refreshTimeline.setCycleCount(Animation.INDEFINITE);

        refreshTimeline.play();
    }

    public void refreshInbox() {

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

    public void clearMailBox(){
        inboxRDB.clearMail();
    }









}