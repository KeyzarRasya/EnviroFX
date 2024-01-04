package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class UserProfilController {

    public User user;

    @FXML
    private Label id;
    @FXML
    private Label username;
    @FXML
    private Label email;
    @FXML
    private Label point;
    @FXML
    private Label balance;
    @FXML
    private Button topup;
    @FXML
    private Button back;

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize(){
        setUser(LoginController.user);
        id.setText(String.valueOf(user.getId()));
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        point.setText(String.valueOf(user.getPoint()));
        balance.setText("Rp. "+user.getMoney());
    }

    public void onTopUp(){
        openNewPage(topup, "topUp.fxml");
    }

    public void onBack(){
        openNewPage(back, "homePage.fxml");
    }

    private void openNewPage(Region pane, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) pane.getScene().getWindow();
            currentStage.setFullScreen(true);
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
