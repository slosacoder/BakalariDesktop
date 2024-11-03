package xyz.slosa.endpoints.http;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * @author : slosa
 * @created : 03.11.24, Sunday
 * CC BakalariDesktop's contributors, use according to the license!
 **/

/**
 * A Main class for accessing the api, contains also all the logic for http handling, proceed with caution
 */
public class BakalariAPI {

    private final String schoolURL;

    private static final String CLIENT_ID = "ANDR";
    private final Consumer<String> LOGGER;

    public BakalariAPI(final String schoolURL, final Consumer<String> logger) {
        this.schoolURL = schoolURL;
        this.LOGGER = logger;
    }

    public void request(AbstractBakaHttpRequest httpRequest) throws IOException, InterruptedException {
        // FINDME :: http deserialization logic
        final HttpClient client = HttpClient.newHttpClient();

        // Prepare the request body
        final String requestBody = String.format(httpRequest.getEndpoint(), // Set the endpoint and encode only the client_id
                URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8));

        // Create the POST request
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(schoolURL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // Send the request and get the response
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response status is 200 (OK), pass it to the deserialization mechanism
        if (response.statusCode() == 200) {
            httpRequest.setObject(httpRequest.deserialize(new JSONObject(response.body())));
            httpRequest.setHandled(); // make it actually usable
            LOGGER.accept("Successfully received response: " + response.body());
        } else {
            // Handle error responses
            LOGGER.accept("Login failed: " + response.statusCode());
            LOGGER.accept("Response: " + response.body());
        }

    }
}
