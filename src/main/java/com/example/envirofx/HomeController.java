package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Charity;
import model.User;
import model.Vehicle;
import org.json.JSONArray;
import org.json.JSONObject;

public class HomeController {

    @FXML
    private Pane charity;

    @FXML
    private Pane edu;

    @FXML
    private Pane weather;

    @FXML
    private Pane rent;

    @FXML
    private Button text;

    @FXML
    private AnchorPane root;

    @FXML
    private Circle profil;

    public static String charityList;

    public static List<Vehicle> allVehicle = new ArrayList<>();
    public static List<Charity> allCharity = new ArrayList<>();

    public static void setCharityList(String charityList) {
        HomeController.charityList = charityList;
    }

    public void initialize() {
        charity.setOnMouseClicked(this::charityClicked);
        edu.setOnMouseClicked(this::eduClicked);
        weather.setOnMouseClicked(this::weatherClicked);
        rent.setOnMouseClicked(this::rentClicked);
        User user = LoginController.user;
        profil.setOnMouseClicked(this::profilClicked);
        text.setText(user.getUsername());
        System.out.println(user.getUsername());
        LoginController.sendLoginAgain(user.getUsername(), user.getPassword(), false);
    }

    public void profilClicked(MouseEvent event){
        openNewPage(this.profil, "userProfile.fxml");
    }

    public void charityClicked(MouseEvent event) {
        allCharity = new ArrayList<>();
        getAllCharity();
        Iterator<Charity> iterator = HomeController.allCharity.iterator();
        while (iterator.hasNext()){
            Charity charity1 = iterator.next();
            System.out.println("Charity"+charity1.getTitle());
        }
        openNewPage(charity, "charityPage.fxml");
    }

    public void eduClicked(MouseEvent event) {
        System.out.println("EDU");
        openNewPage(edu, "topUp.fxml");
    }

    public void weatherClicked(MouseEvent event) {
        System.out.println("WEATHER");
        openNewPage(this.weather, "wheaterPage.fxml");
    }

    public void rentClicked(MouseEvent event) {
        System.out.println("RENT");
        allVehicle = new ArrayList<>();
        getAllVehicle();
        openNewPage(this.rent, "rentPage.fxml");
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

    private void openNewPage(Circle pane, String fxml) {
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

    public void getAllVehicle() {
        try {
            URL url = new URL("http://localhost:8080/vehicle/all"); // Ganti dengan URL sesuai endpoint Anda
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Vehicle vehicle = new Vehicle();
                vehicle.setId(jsonObject.getLong("id"));
                vehicle.setType(jsonObject.getString("type"));
                vehicle.setAvailable(jsonObject.getBoolean("isAvailable"));
                vehicle.setCost(jsonObject.getInt("cost"));
                allVehicle.add(vehicle);
                System.out.println(jsonObject.getString("type"));
            }
            setCharityList(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllCharity() {
        try {
            URL url = new URL("http://localhost:8080/charity/list/non/verified"); // Ganti dengan URL sesuai endpoint Anda
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Charity charity1 = new Charity();
                JSONObject host = jsonObject.getJSONObject("host");
                charity1.setId(jsonObject.getLong("id"));
                charity1.setTitle(jsonObject.getString("title"));
                charity1.setCharityAmount(jsonObject.getBigDecimal("charityAmount"));
                charity1.setFundedMoney(jsonObject.getBigDecimal("fundedMoney"));
                charity1.setHost(host.getString("username"));
                charity1.setDescription(jsonObject.getString("description"));
                allCharity.add(charity1);
            }
            setCharityList(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
