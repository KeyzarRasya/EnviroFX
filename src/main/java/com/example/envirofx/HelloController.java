package com.example.envirofx;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField emailField;


    @FXML
    protected void hello(){
        welcomeText.setText("Hello");
    }

    @FXML
    protected void onHelloButtonClick() {
        System.out.println("Email entered : " + emailField.getText());
    }

    @FXML
    protected void showInfo(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Button Clicked");
        alert.setContentText("MEOWWWWWW");
        alert.show();
    }
}