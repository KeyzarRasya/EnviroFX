package com.example.envirofx;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
public class HelperClass {

    private int responseCode;

    public int getResponseCode() {
        return this.responseCode;
    }

    public void createAccount(String username, String email, String password){
        try {
            URL url = new URL("http://localhost:8080/user/create");


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            String requestBody = "{\"username\": \"" + username + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
            byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(requestBodyBytes.length);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBodyBytes);
            }
            int responseCode = connection.getResponseCode();
            this.responseCode = responseCode;
            System.out.println("Response Code: " + responseCode);

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
