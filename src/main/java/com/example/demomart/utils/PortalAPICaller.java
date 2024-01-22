package com.example.demomart.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PortalAPICaller {

    public static BigDecimal getDiscountFromPortal(BigDecimal subtotal) throws IOException {
        BigDecimal discount = BigDecimal.ZERO; // Default discount value


        String discountApiEndPoint = "http://localhost:80/api/discount";

        URL url = new URL(discountApiEndPoint);
        HttpURLConnection connection = (HttpURLConnection)  url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String cartJson= "{\"subtotal\": " + subtotal + "}";

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = cartJson.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(br);

            if (node.has("discount")) {
                discount = node.get("discount").decimalValue();
            }
            System.out.println(discount);
            connection.disconnect();
        } catch (IOException e) {
            System.out.println(e);
        }

        return discount;
    }
}



