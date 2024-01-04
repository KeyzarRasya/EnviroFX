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
            // URL endpoint
            URL url = new URL("http://localhost:8080/user/create");

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable output and set content length
            connection.setDoOutput(true);
            String requestBody = "{\"username\": \"" + username + "\", \"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
            byte[] requestBodyBytes = requestBody.getBytes(StandardCharsets.UTF_8);
            connection.setFixedLengthStreamingMode(requestBodyBytes.length);

            // Write request body
            try (OutputStream os = connection.getOutputStream()) {
                os.write(requestBodyBytes);
            }

            // Get response code
            int responseCode = connection.getResponseCode();
            this.responseCode = responseCode;
            System.out.println("Response Code: " + responseCode);

            // Close connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
