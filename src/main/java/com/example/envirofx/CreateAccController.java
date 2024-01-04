package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateAccController {

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField;

    public void onCreate(){
        HelperClass ds = new HelperClass();
        ds.createAccount(username.getText(), email.getText(), passwordField.getText());
    }

}
