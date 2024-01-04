package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class CreateCharityController {

    @FXML
    private Text char1;

    @FXML
    private Pane root;

    @FXML
    private Button create;

    @FXML
    private TextField title;

    @FXML
    private TextField amount;

    @FXML
    private TextArea description;
    @FXML
    private Button upload;
    @FXML
    private Label status;
    @FXML
    private Button charity;
    @FXML
    private Button edu;
    @FXML
    private Button weather;
    @FXML
    private Button rent;
    @FXML
    private Label home;

    public void onCharity(){
        openNewPage(this.charity, "charityPage.fxml");
    }

    public void onWeather(){
        openNewPage(this.weather, "wheaterPage.fxml");
    }

    public void onEdu(){
        openNewPage(this.edu, "eduquizPage.fxml");
    }

    public void onRent(){
        openNewPage(this.rent, "rentPage.fxml");
    }

    public void initialize(){
        status.setText("");
        home.setOnMouseClicked(this::onHome);
    }

    public void onHome(MouseEvent mouseEvent){
        openNewPage(this.home, "homePage.fxml");
    }

    private void openNewPage(Region region, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);
            Stage currentStage = (Stage) region.getScene().getWindow();
            currentStage.setFullScreen(true);
            currentStage.setScene(newScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUpload() throws IOException {
        User user = LoginController.user;
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        try {
            URL url = new URL("http://localhost:8080/charity/create");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic " + encodeCredentials(user.getUsername(), user.getPassword()));

            // Buat body request
            String requestBody = String.format("{\"title\":\"%s\",\"description\":\"%s\",\"charityAmount\":\"%s\"}", title.getText(), description.getText(), new BigDecimal(amount.getText()));

            // Aktifkan output untuk mengirimkan body request
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(requestBody);
            wr.flush();
            wr.close();

            // Baca response
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print response
            System.out.println("Response : " + response.toString());
            if(responseCode == 200){
                status.setText("Create Successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fungsi untuk encode username dan password menjadi Basic Auth
    private static String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        return Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
