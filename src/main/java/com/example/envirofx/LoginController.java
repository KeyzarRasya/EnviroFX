package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signup;

    public static String userData;

    public static User user;

    public static String usernames;
    public static String password;


    @FXML
    protected void hello(){
        welcomeText.setText("Hello");
    }
    @FXML
    protected void showInfo(MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Button Clicked");
        alert.setContentText("MEOWWWWWW");
        alert.show();
    }

    public void onLogin(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        handleLogin(username, password, true);
    }

    @FXML
    public void handleLogin(String usernames, String passwords, boolean isFirst) {
        String username = usernames;
        String password = passwords;

        // Panggil metode untuk mengirim permintaan login ke Spring Boot
        sendLoginRequest(username, password, isFirst);
    }

    private void sendLoginRequest(String username, String password, boolean isFirst) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/user/authenticate/" + username + "/" + password))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            setUserData(response.body());
            // Handle response, misalnya, tampilkan pesan ke pengguna
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            if(response.statusCode() == 200 && isFirst){
                user = new User();
                setUser();
                openHomePage(loginButton, "homePage.fxml");
            }else if(response.statusCode() == 200 && !isFirst){
                user = new User();
                setUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void sendLoginAgain(String username, String password, boolean isFirst) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/user/authenticate/" + username + "/" + password))
                .header("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            setUserData(response.body());
            // Handle response, misalnya, tampilkan pesan ke pengguna
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
            if(response.statusCode() == 200 && !isFirst){
                user = new User();
                setUser();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUserData(String userData) {
        LoginController.userData = userData;
    }

    public static void setUser() {
        JSONObject jsonObject = new JSONObject(LoginController.userData);
        JSONObject principalObject = jsonObject.getJSONObject("principal");
        user.setEmail(principalObject.getString("email"));
        user.setId(principalObject.getLong("id"));
        user.setMoney(principalObject.getBigDecimal("money"));
        user.setUsername(principalObject.getString("username"));
        user.setPoint(principalObject.getInt("point"));
        user.setPassword(principalObject.getString("password"));
    }

    public void onSignUp(){
        openHomePage(this.signup, "createAccount.fxml");
    }


}