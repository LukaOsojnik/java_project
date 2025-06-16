package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.enum1.County;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyPurpose;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyType;
import hr.javafx.realestate.javafxmanagementsystem.model.Address;
import hr.javafx.realestate.javafxmanagementsystem.model.Property;
import hr.javafx.realestate.javafxmanagementsystem.dbrepository.AddressRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.dbrepository.PropertyRepositoryDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.math.BigDecimal;

public class AddPropertyController {
    @FXML private TextField propertyAreaTextField;
    @FXML private TextField priceTextField;
    @FXML private ComboBox<PropertyType> propertyTypeComboBox;
    @FXML private ComboBox<PropertyPurpose> propertyPurposeComboBox;

    @FXML private TextField streetTextField;
    @FXML private TextField numberTextField;
    @FXML private TextField cityTextField;
    @FXML private ComboBox<County> countyComboBox;

    ValidationSupport validationSupport = new ValidationSupport();

    public void initialize(){
        countyComboBox.getItems().addAll(County.values());
        propertyTypeComboBox.getItems().addAll(PropertyType.values());
        propertyPurposeComboBox.getItems().addAll(PropertyPurpose.values());

        validationSupport.registerValidator(streetTextField,
                false, Validator.createEmptyValidator("Unos ulice je obvezan!"));
        validationSupport.registerValidator(numberTextField,
                false, Validator.createEmptyValidator("Unos kućnog broja je obvezan!"));
        validationSupport.registerValidator(cityTextField,
                false,  Validator.createEmptyValidator("Unos grada je obvezan!"));
        validationSupport.registerValidator(countyComboBox,
                false, Validator.createEmptyValidator("Odabir županije je obvezan!"));
        validationSupport.registerValidator(propertyAreaTextField,
                false, Validator.createEmptyValidator("Unos površine je obvezan!"));
        validationSupport.registerValidator(priceTextField,
                false, Validator.createEmptyValidator("Unos cijene je obvezan!"));
        validationSupport.registerValidator(propertyTypeComboBox,
                false, Validator.createEmptyValidator("Odabir tipa imovine je obvezan!"));
        validationSupport.registerValidator(propertyPurposeComboBox,
                false, Validator.createEmptyValidator("Odabir svrhe imovine je obvezan!"));


    }
    public void saveAddress(){
        Boolean isValid = validationSupport.isInvalid();
        if(Boolean.TRUE.equals(isValid)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreške kod unosa nove imovine");
            alert.setHeaderText("Imovina nije spremljena.");
            alert.setContentText("Unesite sva potrebna polja kako bi spremili imovinu");
            alert.showAndWait();
        }

        else{
            AddressRepositoryDatabase<Address> addressRepositoryDatabase = new AddressRepositoryDatabase<>();
            PropertyRepositoryDatabase<Property> propertyRepositoryDatabase = new PropertyRepositoryDatabase<>();

            String street = streetTextField.getText();
            String number = numberTextField.getText();
            String city = cityTextField.getText();
            County county = countyComboBox.getSelectionModel().getSelectedItem();
            PropertyType propertyType = propertyTypeComboBox.getSelectionModel().getSelectedItem();
            PropertyPurpose propertyPurpose = propertyPurposeComboBox.getSelectionModel().getSelectedItem();
            String area = propertyAreaTextField.getText();
            String price = priceTextField.getText();

            Address address = Address.builder()
                    .streetName(street)
                    .streetNumber(number)
                    .city(city)
                    .county(county)
                    .build();
            addressRepositoryDatabase.save(address);

            Address propertyAddress = addressRepositoryDatabase.returnLast();

            Property property = new Property(new BigDecimal(area), new BigDecimal(price), propertyAddress, propertyType, propertyPurpose);
            propertyRepositoryDatabase.save(property);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje imovine");
            alert.setHeaderText("Imovina je spremljena");
            StringBuilder sb = new StringBuilder();
            sb.append("Imovina s adrese " + street + " " + number)
                    .append(" u gradu " + city + " je spremljena!");
            alert.setContentText(sb.toString());
            alert.showAndWait();

        }

        cityTextField.clear();
        numberTextField.clear();
        propertyAreaTextField.clear();
        priceTextField.clear();
        streetTextField.clear();
        countyComboBox.getSelectionModel().clearSelection();
        propertyPurposeComboBox.getSelectionModel().clearSelection();
        propertyTypeComboBox.getSelectionModel().clearSelection();


    }
}
