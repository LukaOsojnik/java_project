package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;
import hr.javafx.realestate.javafxmanagementsystem.dbrepository.LeaseRepositoryDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;



public class SearchLeaseAgreementController {

    @FXML private TextField nameTextField;

    @FXML private TableView<LeaseAgreement> leaseAgreementTableView;
    @FXML private TableColumn<LeaseAgreement, String> leaseAgreementId;
    @FXML private TableColumn<LeaseAgreement, String> leaseAgreementNameColumn;
    @FXML private TableColumn<LeaseAgreement, String> addressColumn;
    @FXML private TableColumn<LeaseAgreement, String> leaseAgreementDateColumn;
    @FXML private TableColumn<LeaseAgreement, String> leaseAgreementPriceColumn;

    private LeaseAgreement selectedLeaseAgreement;


    public void initialize(){
        leaseAgreementId.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getId().toString()));
        leaseAgreementNameColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getTenant().getFullName()));
        addressColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getProperty().getAddress().toString()));
        leaseAgreementDateColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getSigningDate().toString()));
        leaseAgreementPriceColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getRentPrice().toString()));

        leaseAgreementTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                LeaseAgreement selected = leaseAgreementTableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selectedLeaseAgreement = selected;
                }
            }
        });
    }

    public void filterLease(){
        LeaseRepositoryDatabase<LeaseAgreement> lrd = new LeaseRepositoryDatabase<>();
        List<LeaseAgreement> leaseAgreementList = lrd.findAll();

        String name = nameTextField.getText();
        if(!name.isEmpty()){
            leaseAgreementList = leaseAgreementList.stream()
                    .filter(l -> l.getTenant().getFullName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        }
        ObservableList<LeaseAgreement> observableListLease = FXCollections.observableList(leaseAgreementList);
        leaseAgreementTableView.setItems(observableListLease);
    }



    public void editLeaseScreen() throws IOException {

        if (selectedLeaseAgreement != null) {
            MenuController<LeaseAgreement> menuController = new MenuController<>();
            menuController.showEditLease(selectedLeaseAgreement);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod odabira ugovora!");
            alert.setHeaderText("Ugovor nije izabran.");
            alert.setContentText("Označite ugovor kako bi ga uređivali.");
            alert.showAndWait();
        }

    }

}
