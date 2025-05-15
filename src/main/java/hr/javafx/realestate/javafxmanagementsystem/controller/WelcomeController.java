package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.FileRepository.LoginRepository;
import hr.javafx.realestate.javafxmanagementsystem.exception.FailedToAuthenticateException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class WelcomeController {

    @FXML TextField usernameTextField;

    @FXML PasswordField passwordField;

    MenuController menuController = new MenuController();

    public void logIn() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        String username = usernameTextField.getText();
        String password = passwordField.getText();

        String[] result;

        try{
            result = LoginRepository.checkLogIn(username, password);

            if(result[1].equals("user")){
                menuController.showSearchUserPropertyScreen();
            }
            if(result[1].equals("admin")){
                menuController.showSearchPropertyScreen();
            }


        } catch(FailedToAuthenticateException e){
            stringBuilder.append("Wrong authentication.")
                    .append("\n")
                    .append("Please try again.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login unsuccessful");
            alert.setContentText(stringBuilder.toString());
            alert.showAndWait();
        }





    }




}
