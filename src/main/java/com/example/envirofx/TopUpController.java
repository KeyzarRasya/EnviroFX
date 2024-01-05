package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

public class TopUpController {

    @FXML
    private Button back;
    @FXML
    private Button topup;
    @FXML
    private TextField amount;
    @FXML
    private Label status;

    private User user;

    public void onBack(){
        openNewPage(back, "homePage.fxml");
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initialize(){
        setUser(LoginController.user);
        status.setText("");
    }

    public void onTopUp() throws IOException {
            String url = "http://localhost:8080/user/topup/{amount}"; // Ganti dengan URL sesuai kebutuhan

            String fullUrl = url.replace("{amount}", amount.getText());

            URL obj = new URL(fullUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            String credentials = user.getUsername() + ":" + user.getPassword();
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            con.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            String payload = "{ \"username\": \"" + user.getUsername() + "\", \"amount\": " + amount + " }";

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(payload);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            if(responseCode == 200){
                status.setText("Top Up Berhasil");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("Response : " + response.toString());
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
