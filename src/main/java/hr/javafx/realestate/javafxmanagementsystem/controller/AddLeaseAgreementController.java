package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyStatus;
import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;
import hr.javafx.realestate.javafxmanagementsystem.model.Property;
import hr.javafx.realestate.javafxmanagementsystem.model.Tenant;
import hr.javafx.realestate.javafxmanagementsystem.DbRepository.LeaseRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.DbRepository.PropertyRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.DbRepository.TenantRepositoryDatabase;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AddLeaseAgreementController {

    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField emailTextField;

    @FXML private TextField priceTextField;
    @FXML DatePicker dateOfContract;

    @FXML TableView<Property> propertyTableView;
    @FXML TableColumn<Property, String> propertyIdColumn;
    @FXML TableColumn<Property, String> propertyAddressColumn;
    @FXML TableColumn<Property, String> propertyCountyColumn;
    @FXML TableColumn<Property, String> propertyAreaColumn;

    private Property selectedProperty;

    ValidationSupport validationSupport = new ValidationSupport();

    public void initialize(){

        validationSupport.registerValidator(nameTextField,
                false, Validator.createEmptyValidator("Ime je obvezno polje"));
        validationSupport.registerValidator(surnameTextField,
                false, Validator.createEmptyValidator("Prezime je obvezno polje"));

        validationSupport.registerValidator(phoneTextField, false,
                Validator.createRegexValidator(
                        "Krivi unos broja (primjer: 0981234567)",
                        "^09\\d{7,8}$",
                        Severity.ERROR));

        validationSupport.registerValidator(emailTextField, false,
                Validator.createRegexValidator(
                        "Cijena mora biti broj do dva decimalna mjesta",
                        "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$",
                        Severity.ERROR));

        validationSupport.registerValidator(priceTextField, false,
                Validator.createRegexValidator(
                        "Cijena mora biti broj do dva decimalna mjesta",
                        "^\\d+(\\.\\d{1,2})?$",
                        Severity.ERROR)
        );
        propertyIdColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getId().toString()));
        propertyAddressColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getAddress().toString()));
        propertyCountyColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getAddress().getCounty().toString()));
        propertyAreaColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getMetersSquared().toString()));

        PropertyRepositoryDatabase<Property> propertyRepository = new PropertyRepositoryDatabase<>();
        List<Property> propertyList = propertyRepository.findAll();
        propertyList = propertyList.stream()
                .filter(p -> p.getPropertyStatus().equals(PropertyStatus.AVAILABLE))
                .toList();
        ObservableList<Property> observableList = FXCollections.observableList(propertyList);
        propertyTableView.setItems(observableList);

        propertyTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Property selected = propertyTableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selectedProperty = selected;
                    System.out.println("Selected property ID: " + selectedProperty.getId().toString());
                }
            }
        });
    }

    public void addLeaseAgreement() throws SQLException, IOException {
        if(validationSupport.isInvalid() || selectedProperty == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod kreiranja novog ugovora!");
            alert.setHeaderText("Ugovor nije spremljen.");
            alert.setContentText("Unesite sva potrebna polja kako bi kreirali ugovor.");
            alert.showAndWait();
        }
        else{
            TenantRepositoryDatabase<Tenant> tenantRepository = new TenantRepositoryDatabase<>();
            LeaseRepositoryDatabase<LeaseAgreement> leaseRepository = new LeaseRepositoryDatabase<>();

            String firstName = nameTextField.getText();
            String lastName = surnameTextField.getText();
            String phone = phoneTextField.getText();
            String email = emailTextField.getText();

            Tenant tenantSave = new Tenant(firstName, lastName, phone, email);
            tenantRepository.save(tenantSave);
            Tenant tenant = tenantRepository.returnLast();

            String rentPrice = priceTextField.getText();
            LocalDate signingDate = dateOfContract.getValue();
            LeaseAgreement leaseAgreement = new LeaseAgreement(tenant, selectedProperty, new BigDecimal(rentPrice), signingDate);

            leaseRepository.save(leaseAgreement);
            PropertyRepositoryDatabase<Property> prd = new PropertyRepositoryDatabase();
            prd.changeStatus(selectedProperty);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno kreiran novi ugovor!");
            alert.showAndWait();

            PropertyRepositoryDatabase<Property> propertyRepository = new PropertyRepositoryDatabase<>();
            List<Property> propertyList = propertyRepository.findAll();
            propertyList = propertyList.stream()
                    .filter(p -> p.getPropertyStatus().equals(PropertyStatus.AVAILABLE))
                    .toList();
            ObservableList<Property> observableList = FXCollections.observableList(propertyList);
            propertyTableView.setItems(observableList);


        }
    }



}
