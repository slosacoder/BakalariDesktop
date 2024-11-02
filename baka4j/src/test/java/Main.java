import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

public class Main {

    private static final String API_URL = "https://www.gymnaziumjihlava.cz:81/zak/api/login";
    private static final String CLIENT_ID = "ANDR";

    public static void main(String[] args) {
        try {
            String username = "USERNAME";  // replace with actual username
            String password = "PASSWORD";  // replace with actual password
            Map<String, String> tokens = login(username, password);

            if (tokens != null) {
                System.out.println("Access Token: " + tokens.get("access_token"));
                System.out.println("Refresh Token: " + tokens.get("refresh_token"));
                System.out.println("ID Token: " + tokens.get("id_token"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> login(String username, String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Prepare the request body
        String requestBody = String.format("client_id=%s&grant_type=password&username=%s&password=%s",
                URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8),
                URLEncoder.encode(username, StandardCharsets.UTF_8),
                URLEncoder.encode(password, StandardCharsets.UTF_8));

        // Create the POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response status is 200 (OK)
        if (response.statusCode() == 200) {
            return parseTokens(response.body());
        } else {
            // Handle error responses
            System.out.println("Login failed: " + response.statusCode());
            System.out.println("Response: " + response.body());
            return null;
        }
    }

    private static Map<String, String> parseTokens(String responseBody) {
        // Parse JSON response and extract tokens
        Map<String, String> tokens = new HashMap<>();
        // Assuming you have a JSON parser available like Jackson or Gson
        // Here is an example with Jackson (add it as a dependency if needed)

        // ObjectMapper mapper = new ObjectMapper();
        // Map<String, Object> jsonMap = mapper.readValue(responseBody, new TypeReference<Map<String,Object>>(){});
        // tokens.put("access_token", (String) jsonMap.get("access_token"));
        // tokens.put("refresh_token", (String) jsonMap.get("refresh_token"));
        // tokens.put("id_token", (String) jsonMap.getOrDefault("id_token", null));

        // For simplicity, let's assume a custom method is used here or use Gson similarly
        // You can replace this with actual parsing code

        // Example output assuming the response parsing is complete
        tokens.put("access_token", "ACCESS_TOKEN_EXAMPLE");
        tokens.put("refresh_token", "REFRESH_TOKEN_EXAMPLE");
        tokens.put("id_token", "ID_TOKEN_EXAMPLE");

        return tokens;
    }
}
