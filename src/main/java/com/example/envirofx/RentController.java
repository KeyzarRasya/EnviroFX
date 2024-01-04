package com.example.envirofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Vehicle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RentController {

    @FXML
    private Pane content;
    @FXML
    private Label home;

    public ChoiceBox<String> choiceBox = new ChoiceBox<>();

    public Vehicle vehicle;

    @FXML
    private TextField price;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void initialize(){
        home.setOnMouseClicked(this::onHome);
        choiceBox.setLayoutX(55.0);
        choiceBox.setLayoutY(152.0);
        choiceBox.setPrefHeight(56.0);
        choiceBox.setPrefWidth(385.0);
        choiceBox.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 50;");

        // Create a map to associate each item with its corresponding Vehicle
        Map<String, Vehicle> vehicleMap = new HashMap<>();
        for (Vehicle vehicle : HomeController.allVehicle) {
            choiceBox.getItems().add(vehicle.getType());
            vehicleMap.put(vehicle.getType(), vehicle);
        }

        // Add a listener to the choiceBox
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Vehicle selectedVehicle = vehicleMap.get(newValue);
                if (selectedVehicle != null) {
                    System.out.println("Selected Vehicle: " + selectedVehicle.getType());
                    price.setText(String.valueOf(selectedVehicle.getCost()));
                    setVehicle(selectedVehicle);
                    // You can use the selected vehicle (selectedVehicle) here as needed
                }
            }
        });
        content.getChildren().add(choiceBox);
    }

    public void onHome(MouseEvent event){
        openNewPage(home, "homePage.fxml");
    }
    public void onRent(){
        try {
            URL url = new URL("http://localhost:8080/vehicle/rent/" + LoginController.user.getUsername() + "/" + getVehicle().getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // Set any request headers if needed
            conn.setRequestProperty("Content-Type", "application/json");

            // If you need to send any request body data, you can do so here
            // For example, if you need to send JSON data
            String requestBody = "{\"key1\": \"value1\", \"key2\": \"value2\"}";
            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
