package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.dbrepository.PropertyRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.enum1.County;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyType;
import hr.javafx.realestate.javafxmanagementsystem.model.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.List;

public class SearchUserPropertyController {

    @FXML private TextField areaTextField;
    @FXML private TextField addressTextField;
    @FXML private ComboBox<County> countyComboBox;
    @FXML private ComboBox<PropertyType> propertyTypeComboBox;

    @FXML private TableView<Property> tableView;
    @FXML private TableColumn<Property, String> propertyIdColumn;
    @FXML private TableColumn<Property, String> propertyAreaColumn;
    @FXML private TableColumn<Property, String> propertyCountyColumn;
    @FXML private TableColumn<Property, String> propertyPurposeColumn;
    @FXML private TableColumn<Property, String> propertyTypeColumn;
    @FXML private TableColumn<Property, String> propertyAddressColumn;
    @FXML private TableColumn<Property, String> propertyStatusColumn;


    public void initialize(){

        countyComboBox.getItems().addAll(County.values());
        propertyTypeComboBox.getItems().addAll(PropertyType.values());

        propertyIdColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getId().toString()));
        propertyAreaColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getMetersSquared().toString()));
        propertyCountyColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getAddress().getCounty().toString()));
        propertyPurposeColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPropertyPurpose().toString()));
        propertyTypeColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPropertyType().toString()));
        propertyAddressColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getAddress().toString()));
        propertyStatusColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPropertyStatus().toString()));

    }
    public void filterProperty(){
        PropertyRepositoryDatabase<Property> prd = new PropertyRepositoryDatabase<>();

        List<Property> listProperty = prd.findAll();

        String area = areaTextField.getText();
        String address = addressTextField.getText();
        PropertyType type = propertyTypeComboBox.getSelectionModel().getSelectedItem();
        County county = countyComboBox.getSelectionModel().getSelectedItem();

        if (!area.isEmpty()) {
            BigDecimal propertyArea = new BigDecimal(area);
            listProperty = listProperty.stream()
                    .filter(p -> p.getMetersSquared().compareTo(propertyArea) > 0)
                    .toList();
        }

        if(!address.isEmpty()){
            listProperty = listProperty.stream()
                    .filter(p -> p.getAddress().toString().toLowerCase().contains(address.toLowerCase()))
                    .toList();
        }
        if(type != null){
            listProperty = listProperty.stream()
                    .filter(p -> p.getPropertyType().toString().equals(type.toString()))
                    .toList();
        }
        if(county != null){
            listProperty = listProperty.stream()
                    .filter(p -> p.getAddress().toString().equals(county.toString()))
                    .toList();
        }


        ObservableList<Property> observableList = FXCollections.observableList(listProperty);
        tableView.setItems(observableList);

        propertyTypeComboBox.getSelectionModel().clearSelection();
        countyComboBox.getSelectionModel().clearSelection();

    }

}
