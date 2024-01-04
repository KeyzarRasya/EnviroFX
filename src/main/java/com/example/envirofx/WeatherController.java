package com.example.envirofx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WeatherController {


    @FXML
    private Label text;

    public String responseTemp;

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

    public String getResponseTemp() {
        return responseTemp;
    }

    public void initialize() throws IOException {
        home.setOnMouseClicked(this::onHome);
        fetchCuaca();
        text.setText(getResponseTemp());
    }

    public void setResponseTemp(String responseTemp) {
        this.responseTemp = responseTemp;
    }

    public void fetchCuaca() throws IOException {
        // Buat URL dengan parameter query
        String endpoint = "https://cuaca-gempa-rest-api.vercel.app/weather";
        String kabkota = "Purwakarta";
        String prov = "Jawa-Barat";
        String urlString = String.format("%s/%s/%s", endpoint, prov, kabkota);

        // Buat objek URL dari string URL
        URL url = new URL(urlString);

        // Buka koneksi HTTP
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Setel metode permintaan ke GET
        connection.setRequestMethod("GET");

        // Setel header Apikey

        // Baca respons dari server
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Tampilkan respons

        getTemp(response.toString());

        // Tutup koneksi
        connection.disconnect();
    }

    public void getTemp(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON response
        JsonNode jsonNode = objectMapper.readTree(response);

        // Navigate to the "Temperature" parameter
        JsonNode temperatureParameter = null;
        for (JsonNode param : jsonNode.path("data").path("params")) {
            if (param.path("id").asText().equals("t")) {
                temperatureParameter = param;
                break;
            }
        }

        if (temperatureParameter != null) {
            // Get the "times" array under the "Temperature" parameter
            JsonNode temperatureTimes = temperatureParameter.path("times");

            // Get the temperature in Celsius from the first entry
            String temperatureCelsius = temperatureTimes.get(0).path("celcius").asText();
            setResponseTemp(temperatureCelsius);
            System.out.println(getResponseTemp());
            // Print the temperature in Celsius

        } else {
            System.out.println("Temperature parameter not found");
        }

    }
}
