package com.vhbarros.belvo.FinaceAPIIntegration.backend.model.connect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vhbarros.belvo.FinaceAPIIntegration.backend.model.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.vhbarros.belvo.client.invoker.ApiClient;
import com.vhbarros.belvo.client.invoker.auth.Authentication;

public class BelvoAPIClient {
    String id = "8d0a440c-6ab7-401d-bce1-bef930e1b5bc";
    String secret ="HBtKJEMQ*bemSsoUMjXE*g0velRBBQF7o1T6sgE_BR-asctRkL4Vl6e-1EkCZ0DK";
    String scope ="read_institutions,write_links,read_links";

    public Token getAccessToken() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode requestToken = mapper.createObjectNode();
        requestToken.put("id", id);
        requestToken.put("password", secret);
        requestToken.put("scopes", scope);
        String jsonInputString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestToken);
        URL url = new URL ("https://sandbox.belvo.com/api/token/");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Host", "sandbox.belvo.com");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);

        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        Token token = new Token();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            token = mapper.readValue(response.toString(),Token.class);
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return token;
    }

    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setUsername("8d0a440c-6ab7-401d-bce1-bef930e1b5bc");
        apiClient.setPassword("HBtKJEMQ*bemSsoUMjXE*g0velRBBQF7o1T6sgE_BR-asctRkL4Vl6e-1EkCZ0DK");

        return apiClient;
    }
}
