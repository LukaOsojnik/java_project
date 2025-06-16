package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.filerepository.LoginRepository;
import hr.javafx.realestate.javafxmanagementsystem.exception.FailedToAuthenticateException;
import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;
import hr.javafx.realestate.javafxmanagementsystem.model.LogIn;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class WelcomeController {

    @FXML TextField usernameTextField;
    @FXML PasswordField passwordField;

    MenuController<LeaseAgreement> menuController = new MenuController<>();

    public void logIn() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        try{
            LogIn logIn = LoginRepository.checkLogIn(username, password);

            if(logIn.getRole().equals("user")){
                menuController.showSearchUserPropertyScreen();
            }
            if(logIn.getRole().equals("admin")){
                menuController.showSearchPropertyScreen();
            }

        } catch(FailedToAuthenticateException _){
            stringBuilder.append("Wrong authentication.")
                    .append("\n")
                    .append("Please try again.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login unsuccessful");
            alert.setContentText(stringBuilder.toString());
            alert.showAndWait();
            logger.error("Login unsuccessful");
        }
    }
}