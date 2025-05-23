package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.DbRepository.InboxRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.DbRepository.InvoiceRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.model.*;
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
import java.time.LocalDateTime;
import java.util.List;

public class SearchInboxController {

    @FXML private TableView<InboxMessage> inboxTableView;
    @FXML private TableColumn<InboxMessage, String> invoiceIdColumn;
    @FXML private TableColumn<InboxMessage, String> creationDateColumn;
    @FXML private TableColumn<InboxMessage, String> messageColumn;

    private static final String INBOX_MESSAGE = "Imate nedospjelu ratu. Molim Vas podmirite račun.";
    Long selectedInvoiceId;
    private Timeline refreshTimeline;


    public void initialize() {

        invoiceIdColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getInvoiceId().toString()));
        messageColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMessage()));
        creationDateColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getReminderDate().toString()));

        InboxRepositoryDatabase<InboxMessage> ird = new InboxRepositoryDatabase<>();
        List<InboxMessage> listOfMessages = ird.findAll();
        ObservableList<InboxMessage> observableList = FXCollections.observableList(listOfMessages);
        inboxTableView.setItems(observableList);

        inboxTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                InboxMessage selected = inboxTableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selectedInvoiceId = selected.getInvoiceId();
                    System.out.println("Selected invoice ID: " + selectedInvoiceId);
                }
            }
        });

        refreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    try {
                        refreshInbox();
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                })
        );

        refreshTimeline.setCycleCount(Timeline.INDEFINITE);

        refreshTimeline.play();
    }

    public void refreshInbox() throws SQLException, IOException {
        InboxRepositoryDatabase<InboxMessage> inrd = new InboxRepositoryDatabase<>();
        List<InboxMessage> inboxList;

        inboxList = inrd.findAll();
        List<Long> inboxInvoiceId = inboxList.stream()
                .map(InboxMessage::getInvoiceId)
                .toList();

        InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase<>();
        List<Invoice> invoiceList = ird.findAll();

        for(Invoice i : invoiceList) {
            if(!inboxInvoiceId.contains(i.getId()) && !i.isPaid()) {
                InboxMessage inboxMsg = new InboxMessage(i.getId(), INBOX_MESSAGE, LocalDate.now());
                inboxList.add(inboxMsg);
                inrd.save(inboxMsg);
            }
        }


        List<InboxMessage> listOfUnpaid = inrd.checkForUnpaid();

        for(InboxMessage i : listOfUnpaid) {
            inrd.save(i);
        }

        inboxList = inrd.findAll();

        ObservableList<InboxMessage> observableList = FXCollections.observableList(inboxList);
        inboxTableView.setItems(observableList);

    }









}