package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.enum1.County;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyStatus;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyType;
import hr.javafx.realestate.javafxmanagementsystem.model.Property;
import hr.javafx.realestate.javafxmanagementsystem.DbRepository.PropertyRepositoryDatabase;
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

public class SearchPropertyController {

    @FXML private TextField areaTextField;
    @FXML private TextField priceTextField;
    @FXML private ComboBox<County> countyComboBox;
    @FXML private ComboBox<PropertyType> propertyTypeComboBox;
    @FXML private ComboBox<PropertyStatus> propertyStatusComboBox;

    @FXML private TableView<Property> tableView;
    @FXML private TableColumn<Property, String> propertyIdColumn;
    @FXML private TableColumn<Property, String> propertyAreaColumn;
    @FXML private TableColumn<Property, String> propertyCountyColumn;
    @FXML private TableColumn<Property, String> propertyPurposeColumn;
    @FXML private TableColumn<Property, String> propertyTypeColumn;
    @FXML private TableColumn<Property, String> propertyStatusColumn;
    @FXML private TableColumn<Property, String> propertyPriceColumn;


    public void initialize(){

        countyComboBox.getItems().addAll(County.values());
        propertyTypeComboBox.getItems().addAll(PropertyType.values());
        propertyStatusComboBox.getItems().addAll(PropertyStatus.values());

        propertyIdColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getId().toString()));
        propertyAreaColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getMetersSquared().toString()));
        propertyPriceColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPrice().toString()));
        propertyCountyColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getAddress().getCounty().toString()));
        propertyPurposeColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPropertyPurpose().toString()));
        propertyTypeColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPropertyType().toString()));
        propertyStatusColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getPropertyStatus().toString()));

    }
    public void filterProperty(){
        PropertyRepositoryDatabase<Property> prd = new PropertyRepositoryDatabase<>();

        List<Property> listProperty = prd.findAll();

        String area = areaTextField.getText();
        String price = priceTextField.getText();
        PropertyType type = propertyTypeComboBox.getSelectionModel().getSelectedItem();
        County county = countyComboBox.getSelectionModel().getSelectedItem();
        PropertyStatus status = propertyStatusComboBox.getSelectionModel().getSelectedItem();

        if (!area.isEmpty()) {
            BigDecimal propertyArea = new BigDecimal(area);
            listProperty = listProperty.stream()
                    .filter(p -> p.getMetersSquared().compareTo(propertyArea) > 0)
                    .toList();
        }

        if(!price.isEmpty()){
            listProperty = listProperty.stream()
                    .filter(p -> p.getPrice().compareTo(new BigDecimal(price)) < 0)
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
        if(status != null){
            listProperty = listProperty.stream()
                    .filter(p -> p.getPropertyStatus().toString().equals(status.toString()))
                    .toList();
        }


        ObservableList<Property> observableList = FXCollections.observableList(listProperty);
        tableView.setItems(observableList);

        propertyTypeComboBox.getSelectionModel().clearSelection();
        countyComboBox.getSelectionModel().clearSelection();
        propertyStatusComboBox.getSelectionModel().clearSelection();

    }

}
