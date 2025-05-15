module hr.javafx.restaurant.javafxrestaurant {
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires java.rmi;
    requires java.desktop;
    requires org.slf4j;
    requires org.apache.commons.codec;


    opens hr.javafx.realestate.javafxmanagementsystem to javafx.fxml;
    exports hr.javafx.realestate.javafxmanagementsystem;
    exports hr.javafx.realestate.javafxmanagementsystem.controller;
    opens hr.javafx.realestate.javafxmanagementsystem.controller to javafx.fxml;
}