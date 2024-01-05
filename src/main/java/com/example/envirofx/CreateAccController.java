package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccController {

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label status;
    @FXML
    private Button login;

    public void initialize(){
        status.setText("");
    }

    public void onCreate(){
        HelperClass ds = new HelperClass();
        ds.createAccount(username.getText(), email.getText(), passwordField.getText());
        if(ds.getResponseCode() == 200){
            status.setText("Account created");
        }
    }

    public void onLogin(){
        openHomePage(login, "loginPage.html");
    }

    private void openHomePage(Region region, String fxml) {
        try {
            // Load the new scene
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource(fxml));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);

            // Get the current stage
            Stage currentStage = (Stage) region.getScene().getWindow();
            currentStage.setFullScreen(true);
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., show an error message to the user
        }
    }

}
