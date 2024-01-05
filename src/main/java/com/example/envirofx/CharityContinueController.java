package com.example.envirofx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Charity;

import java.io.IOException;

public class CharityContinueController {

    @FXML
    private Label id;
    @FXML
    private Label judul;
    @FXML
    private Text description;
    @FXML
    private Label host;
    @FXML
    private Label amount;
    @FXML
    private Label funded;
    @FXML
    private Pane root;
    @FXML
    private Label home;
    @FXML
    private Label lokasi;

    private Charity selectedCharity;

    public void initialize(){
        setSelectedCharity(CharityController.clickedCharity);
        id.setText("ID: "+String.valueOf(selectedCharity.getId()));
        judul.setText(selectedCharity.getTitle());
        description.setText(selectedCharity.getDescription());
        host.setText(selectedCharity.getHost());
        amount.setText("Rp. "+selectedCharity.getCharityAmount());
        funded.setText("Rp. "+selectedCharity.getFundedMoney());
        lokasi.setText(selectedCharity.getKota()+", "+selectedCharity.getProvinsi());
        home.setOnMouseClicked(this::onHome);
    }

    public void onHome(MouseEvent event){
        openNewPage(home, "homePage.fxml");
    }

    public void onDonate(){
        openNewPage(this.root, "charityTransaction.fxml");
    }

    public void setSelectedCharity(Charity selectedCharity) {
        this.selectedCharity = selectedCharity;
    }

    public Charity getSelectedCharity() {
        return selectedCharity;
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
}
