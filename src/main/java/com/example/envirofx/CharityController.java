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
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Charity;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;

public class CharityController {

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
    private Button charity;
    @FXML
    private Button edu;
    @FXML
    private Button weather;
    @FXML
    private Button rent;
    @FXML
    private Label home;

    public static int id;

    public double layoutY = 263.0;

    public static Charity clickedCharity;


    public void onCharity(MouseEvent event){
        openNewPage(this.charity, "charityPage.fxml");
    }

    public void onWeather(MouseEvent event){
        openNewPage(this.weather, "wheaterPage.fxml");
    }

    public void onEdu(MouseEvent event){
        openNewPage(this.edu, "topUp.fxml");
    }

    public void onRent(MouseEvent event){
        openNewPage(this.rent, "rentPage.fxml");
    }

    public void initialize(){
        home.setOnMouseClicked(this::onHome);
        charity.setOnMouseClicked(this::onCharity);
        edu.setOnMouseClicked(this::onEdu);
        rent.setOnMouseClicked(this::onRent);
        addPane();
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

    public void getEssential(){
        JSONArray jsonArray = new JSONArray(HomeController.charityList);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            // Ambil nilai dari kunci lainnya sesuai kebutuhan
            char1.setText(title);
        }
    }

    public void onCreate(){
        openNewPage(this.create, "createCharity.fxml");
    }

    public void addPane(){
        Iterator<Charity> charityIterator = HomeController.allCharity.iterator();
        while(charityIterator.hasNext()){
            Charity charity = charityIterator.next();
            Pane charityBox = new Pane();
            charityBox.setId(String.valueOf(id));
            charityBox.setLayoutX(22.0);
            charityBox.setLayoutY(layoutY);
            charityBox.setPrefHeight(165.0);
            charityBox.setPrefWidth(1246.0);
            charityBox.setStyle("-fx-background-color: #E1B57E; -fx-background-radius: 15;");

            Label titleLabel = new Label(charity.getTitle());
            titleLabel.setLayoutX(40.0);
            titleLabel.setLayoutY(39.0);
            titleLabel.setPrefHeight(56.0);
            titleLabel.setPrefWidth(384.0);
            titleLabel.setTextFill(Color.web("#47754b"));
            titleLabel.setFont(Font.font("System Bold", 50.0));

            Label amountLabel = new Label("Amount : "+charity.getCharityAmount());
            amountLabel.setLayoutX(424.0);
            amountLabel.setLayoutY(39.0);
            amountLabel.setTextFill(Color.web("#47754b"));
            amountLabel.setFont(Font.font(40.0));

            Button donateButton = new Button("Donate");
            donateButton.setLayoutX(1022.0);
            donateButton.setLayoutY(55.0);
            donateButton.setMnemonicParsing(false);
            donateButton.setPrefHeight(65.0);
            donateButton.setPrefWidth(161.0);
            donateButton.setStyle("-fx-background-color: #47754b; -fx-border-radius: 10;");
            donateButton.setTextFill(Color.WHITE);
            donateButton.setFont(Font.font(30.0));
            donateButton.setOnAction(event -> {
                setClickedCharity(charity);
                openNewPage(root, "charityContinue.fxml");
            });

            CharityController.id++;
            this.layoutY+=177.0;
            charityBox.getChildren().addAll(titleLabel, amountLabel, donateButton);
            root.getChildren().add(charityBox);
        }
    }

    public static void setClickedCharity(Charity clickedCharity) {
        CharityController.clickedCharity = clickedCharity;
    }

    public void onDonate(Charity charity){
        System.out.println(charity.getId());
    }


}
