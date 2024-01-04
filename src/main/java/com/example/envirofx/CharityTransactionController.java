package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Charity;
import model.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CharityTransactionController {

    @FXML
    private Label id;
    @FXML
    private Label judul;
    @FXML
    private Label host;
    @FXML
    private TextField donation;
    @FXML
    private Button donate;
    @FXML
    private Button back;
    private Charity selectedCharity;

    public void initialize(){
        setSelectedCharity(CharityController.clickedCharity);
        id.setText("ID :"+selectedCharity.getId());
        judul.setText(selectedCharity.getTitle());
        host.setText(selectedCharity.getHost());
    }

    public void onBack(){
        openNewPage(back, "homePage.fxml");
    }

    public void onDate(){
        System.out.println(donation.getText());
        User user = LoginController.user;
        try {
            // Create URL object
            URL url = new URL("http://localhost:8080/user/donate/"+ selectedCharity.getId() +"/"+donation.getText()); // Replace with actual charityId and amount

            // Create connection object
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method
            conn.setRequestMethod("POST");

            // Set request headers
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + encodeCredentials(user.getUsername(), user.getPassword()));

            // Enable output and write the request body
            conn.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                String requestBody = "{}"; // Empty JSON since no request body is expected in the endpoint
                wr.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            // Get the response code
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Read the response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println("Response : " + response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
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
    public void setSelectedCharity(Charity selectedCharity) {
        this.selectedCharity = selectedCharity;
    }
}
